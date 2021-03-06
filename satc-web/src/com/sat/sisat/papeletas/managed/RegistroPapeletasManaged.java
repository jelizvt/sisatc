package com.sat.sisat.papeletas.managed;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.ValidateInput;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.papeleta.business.CalculoPapeleta;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.papeleta.dto.UbicacionFiscalDTO;
import com.sat.sisat.papeletas.LoadImagePapeletaManager;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.MpClaseLicencia;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.PaDireccion;
import com.sat.sisat.persistence.entity.PaDocuAnexo;
import com.sat.sisat.persistence.entity.PaIncidencia;
import com.sat.sisat.persistence.entity.PaInfraccion;
import com.sat.sisat.persistence.entity.PaLey;
import com.sat.sisat.persistence.entity.PaPapeleta;
import com.sat.sisat.persistence.entity.PaPersona;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.managed.FileUploadManaged;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class RegistroPapeletasManaged extends BaseManaged {

	@EJB
	PapeletaBoRemote papeletaBo;

	@EJB
	CalculoPredialBoRemote calculoPredialBo;
	
	private Integer infraccionIdOrigial;
	private Timestamp fechaInfraccionOriginal;

	private BuscarPersonaDTO datosInfractor;
	private BuscarPersonaDTO datosPropietario;

	private List<SelectItem> lstClaseLicencia = new ArrayList<SelectItem>();
	private Map<String, Integer> mapClaseLicencia = new HashMap<String, Integer>();
	private Map<Integer, String> mapIClaseLicencia = new HashMap<Integer, String>();
	
	private List<SelectItem> lstLey = new ArrayList<SelectItem>();
	private Map<String, Integer> mapLey = new HashMap<String, Integer>();
	private Map<Integer, String> mapILey = new HashMap<Integer, String>();
	
	private HtmlComboBox cmbHtmlTipoServicio;
	
	private List<SelectItem> lstInfraccion = new ArrayList<SelectItem>();
	private Map<String, Integer> mapInfraccion = new HashMap<String, Integer>();
	private Map<Integer, String> mapIInfraccion = new HashMap<Integer, String>();
	private Map<String, PaInfraccion> mapPaInfraccion = new HashMap<String, PaInfraccion>();

	private Integer papeletaId;
	private Integer tipoTransporte;

	private String nroPlaca;
	private String numeroTarjetaPropiedad;

	// private Integer papeletaId;
	private String nroPapeleta;
	// --datos de la licencia de conducir del infractor
	private Boolean sinLicencia;
	private java.util.Date fechaInfraccion;
	//CREADA PARA MANEJAR LOS TRAMOS DE LAS FECHAS DE INFRACCIONES
	private java.util.Date fechaInfraccionTramos;
	private String horaInfraccion;

	private String ley;
	private String infraccion;

	private Double montoSancion;
	private ArrayList<PaDocuAnexo> recordsDocAnexos;
	private Boolean esPropietario;
	
	private Boolean conDj;
	private Boolean declarante;

	private String pageReturn;

	private String imagePapeleta;
	private String flag_pp; 
	private String flag_dj;
	private String flag_declarante;
	//private String flag_pp_registrar;

	private PaIncidencia incidencia;
	private PaPapeleta papeleta;
	private PaDocuAnexo paDocuAnexo;
	private PaPersona paPersona;
	private PaDireccion paDireccion;
	private String glosa;
	private String placaAnterior;

	private Map<Integer, String> mapITipoDocumento = new HashMap<Integer, String>();

	private HtmlInputHidden txtNumeroLicencia = new HtmlInputHidden();
	private HtmlComboBox cmbClase = new HtmlComboBox();
	private Boolean esContribuyente;
	private Boolean disableVehiculoRematado=false; 
	
	private BuscarPersonaDTO infractorOriginal = new BuscarPersonaDTO();
	
	public RegistroPapeletasManaged() {

	}

	@PostConstruct
	public void init() {
		try {
			List<MpClaseLicencia> lstMpClaseLicencia = papeletaBo.getAllMpClaseLicencia();
			Iterator<MpClaseLicencia> it2 = lstMpClaseLicencia.iterator();
			while (it2.hasNext()) {
				MpClaseLicencia obj = it2.next();
				lstClaseLicencia.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getClaseLicenciaId())));
				mapClaseLicencia.put(obj.getDescripcion(), obj.getClaseLicenciaId());
				mapIClaseLicencia.put(obj.getClaseLicenciaId(), obj.getDescripcion());
			}
			
			List<PaLey> lstPaLey = papeletaBo.getAllLeyInfracciones();
			Iterator<PaLey> it7 = lstPaLey.iterator();
			while (it7.hasNext()) {
				PaLey obj = it7.next();
				lstLey.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getLeyId())));
				mapLey.put(obj.getDescripcion().trim(), obj.getLeyId());
				mapILey.put(obj.getLeyId(), obj.getDescripcion().trim());
			}
//			Calendar cal1 = Calendar.getInstance();
//			setFechaInfraccionTramos(cal1.getTime());
			if (lstLey.size() > 0) {
				setLey(lstLey.get(0).getValue().toString());
				//preparaInfracciones(getLey());//CARGA EL COMBO DE INFRACCIONES
			}

			recordsDocAnexos = new ArrayList<PaDocuAnexo>();

			Integer papeletaId = (Integer) getSessionMap().get("papeletaId");
			if (papeletaId != null && papeletaId != Constante.RESULT_PENDING) {
				// Registro por actualizar
				setPapeletaId(papeletaId);
			
				cargar();
			} else {				
				// Nuevo registro
				papeleta = new PaPapeleta();
				setPapeletaId(Constante.RESULT_PENDING);
				incidencia = new PaIncidencia();
				incidencia.setIncidenciaId(Constante.RESULT_PENDING); // de aqui para abajo el cambio
				Calendar cal1 = Calendar.getInstance();
				setFechaInfraccionTramos(cal1.getTime());
				preparaInfracciones(getLey());				
				//setFlag_pp("P�blico");
			}

			List<MpTipoDocuIdentidad> lstTD = new ArrayList<MpTipoDocuIdentidad>();
			lstTD = papeletaBo.getAllMpTipoDocumento();
			Iterator<MpTipoDocuIdentidad> itTD = lstTD.iterator();
			while (itTD.hasNext()) {
				MpTipoDocuIdentidad objTD = itTD.next();
				mapITipoDocumento.put(objTD.getTipoDocIdentidadId(), objTD.getDescrpcionCorta());			
			}			
					
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void validarUltimosDigitosPapeletas() throws Exception {
		//obtener anio de la papeleta para proceder hacer la visualizacion
		String anio_papeleta, verifica_anio;
		verifica_anio = nroPapeleta.substring(nroPapeleta.length() - 3, nroPapeleta.length() - 2);
		if (verifica_anio.equals("-")) {
			anio_papeleta = nroPapeleta.substring(nroPapeleta.length() - 2);}
		else {anio_papeleta = "SN";}
		if (nroPapeleta.equals("_______-__") || nroPapeleta.equals(null) || nroPapeleta.substring(8).equals("__")
				|| nroPapeleta.substring(8, 9).equals("_") || nroPapeleta.substring(9).equals("_")) {
			setNroPapeleta(null);
		} else {
			int valor = Integer.parseInt(String.valueOf(this.getNroPapeleta().substring(7)));
			Calendar c = new GregorianCalendar();
			int annio = Integer.parseInt(String.valueOf(c.get(Calendar.YEAR)).substring(2));
			if (valor > annio) {
				WebMessages.messageError("El numero de papeleta no es valida; Verifique los datos del año");
			} else {
				PaPapeleta papeleta = papeletaBo.getPaPapeletaByNumeroPapeleta1(getNroPapeleta().trim(),
						getPapeletaId() == null ? Constante.RESULT_PENDING : getPapeletaId());
						this.imagePapeleta="http://190.116.36.140/imagenes_papeletas/"+anio_papeleta+"/"+this.nroPapeleta+".jpg";
						System.out.println(this.imagePapeleta);
				if (papeleta != null) {
					this.imagePapeleta="http://190.116.36.140/imagenes_papeletas/"+anio_papeleta+"/"+this.nroPapeleta+".jpg";
					System.out.println(this.imagePapeleta);
					WebMessages.messageError("El número de papeleta : " + papeleta.getNroPapeleta()
							+ " ya se encuentra registrado con fecha de infraccion " + papeleta.getFechaInfraccion());
				}
			}

		}
	}

	public void cargarImagenPapeleta() {
		LoadImagePapeletaManager manager = new LoadImagePapeletaManager();
		String fileName = manager.getFileNamePapeleta(getNroPapeleta());
		if (fileName != null) {
			FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "paramParentFileUpload", "MP");
			FileUploadManaged fileUpload = (FileUploadManaged) getManaged("fileUploadManaged");
			fileUpload.getArchivo().setFile(new File(fileName));
			fileUpload.getArchivo().setFileName(nroPapeleta);
			fileUpload.guardar();
			MedioProbatorioAnexoManaged medioprobatorio = (MedioProbatorioAnexoManaged) getManaged("medioProbatorioAnexoManaged");
			medioprobatorio.setCmbValueTipoDocumento("Papeleta");
			medioprobatorio.getArchivo().setFile(new File(fileName));
			medioprobatorio.getArchivo().setFileName(nroPapeleta + ".jpg");
			medioprobatorio.getPaDocuAnexo().setNumeroDocumento(nroPapeleta);
			medioprobatorio.guardar();

			try {
				ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
						.getContext();
				String path = ctx.getRealPath("/");

				String dtFile = path + "tmp" + File.separator + getNroPapeleta() + ".jpg";
				manager.copyfile(fileName, dtFile);
				setImagePapeleta("\\satc\\tmp\\" + getNroPapeleta() + ".jpg");
			} catch (Exception jre) {
				jre.printStackTrace();
				WebMessages.messageFatal(jre);
			}

		} else {
			WebMessages.messageError("No existe la papaleta Nro : " + getNroPapeleta() + " escaneda ");
		}
	}

	public String salir() {
		String pageReturn = (String) getSessionMap().get("pageReturn");
		if (pageReturn != null && pageReturn.trim().length() > 0) {
			getSessionManaged().setPage(pageReturn);
			getSessionMap().remove("pageReturn");
			closeSession("registroDetalleLotePapeletasManaged");
			closeSession("verificarLotesPapeletasManaged");
			closeSession("digitarLotesPapeletasManaged");
		}
		return sendRedirectPrincipal();
	}

	public void setPersonaPapeleta() {
		String personaPapeleta = FacesUtil.getRequestParameter("personaPapeleta");
		String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");

		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "personaPapeleta", personaPapeleta);

		BuscarPersonaPapeletasManaged buscarPersonaManaged = (BuscarPersonaPapeletasManaged) getManaged("buscarPersonaPapeletasManaged");
		buscarPersonaManaged.setPantallaUso(ReusoFormCns.REGISTRO_PAPELETAS);
		buscarPersonaManaged.setDestinoRefresh(destinoRefresh); 
	}

	/**
	 * Desde la pantalla BuscarPersonaManaged
	 * 
	 * @param persona
	 * @param personaPapeleta
	 */
	public void copiaPersona(BuscarPersonaDTO persona, String personaPapeleta) {
		// personaPapeleta "I" Infractor
		// personaPapeleta "P" Propietario
		if (personaPapeleta.equals("I")) {
			setDatosInfractor(persona);
			setDireccionDesdeMpPersona(persona.getPersonaId(), getDatosInfractor());
			setEsPropietario(Boolean.FALSE);
		} else {
			setDatosPropietario(persona);
			setDireccionDesdeMpPersona(persona.getPersonaId(), getDatosPropietario());
		}
	}

	public void setDireccionDesdeMpPersona(Integer personaId, BuscarPersonaDTO persona) {
		try {
			UbicacionFiscalDTO direccion = papeletaBo.getUbicacionFiscal(personaId);
			if (direccion != null) {
				persona.setDireccionCompleta(direccion.getDireccionCompleta());
				persona.setDireccionId(Constante.RESULT_PENDING);// Siempre se registra una
																	// direccion por papeleta
				persona.setTipoViaId(direccion.getTipoViaId());
				persona.setViaId(direccion.getViaId());
				persona.setLugarId(direccion.getLugarId());
				persona.setNumero(direccion.getNumero());
			} else {
				PaDireccion paDireccion = papeletaBo.getPaDireccion(personaId);
				if (paDireccion != null) {
					persona.setDireccionId(Constante.RESULT_PENDING);// Siempre se registra una
																		// direccion por papeleta
					persona.setTipoViaId(paDireccion.getTipoViaId());
					persona.setViaId(paDireccion.getViaId());
					persona.setLugarId(paDireccion.getLugarId());
					persona.setNumero(paDireccion.getNumero());
					persona.setDireccionCompleta(paDireccion.getDireccionCompleta());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargar() {
		try {
			if (getPapeletaId() != null && getPapeletaId().intValue() > 0) {				
				// Obtener datos de la papeleta
				papeleta = papeletaBo.getPapeleta(getPapeletaId());
				
				//temporal para almacenar la infracción y fecha de registro antes de cualquier cambio. Tal como fue recuperada de la BD
				infraccionIdOrigial=papeleta.getInfraccionId();
				fechaInfraccionOriginal=papeleta.getFechaInfraccion();				
				
				
				setPapeletaId(papeleta.getPapeletaId());
				setNroPapeleta(papeleta.getNroPapeleta());
				setGlosa(papeleta.getGlosa());
				setPlacaAnterior(papeleta.getPlacaAnterior());
				
				
				/**
				 * if(papeleta.getSinLicencia().equals("S")){ setSinLicencia(Boolean.TRUE); }else{
				 * setSinLicencia(Boolean.FALSE); //}
				 */
				setFechaInfraccion(papeleta.getFechaInfraccion());				        

				setHoraInfraccion(papeleta.getHoraInfraccion());
				setFechaInfraccionTramos(papeleta.getFechaInfraccion());
				//obtener anio de la papeleta para proceder hacer la visualizacion
				String anio_papeleta, verifica_anio;
				verifica_anio = papeleta.getNroPapeleta().substring(papeleta.getNroPapeleta().length() - 3, papeleta.getNroPapeleta().length() - 2);
				if (verifica_anio.equals("-")) {
					anio_papeleta = papeleta.getNroPapeleta().substring(papeleta.getNroPapeleta().length() - 2);}
				else {anio_papeleta = "SN";}
				setImagePapeleta("http://190.116.36.140/imagenes_papeletas/"+anio_papeleta+"/"+papeleta.getNroPapeleta()+".jpg");
				
				String FlagDec = papeleta.getFlag_declarante();
				
				if ( FlagDec == null || FlagDec.equals("C"))
				{
					setDeclarante(false);
					flag_declarante = "C";
				}				
				else// (FlagDec.equals("P"))
				{
					setDeclarante(true);
					flag_declarante = "P";
				}
				
				String FlagDj = papeleta.getFlag_dj();
				
				if (FlagDj == null || FlagDj.equals("0"))
				{
					setConDj(false);
					flag_dj = "0";
				}
				
				else //if (FlagDj.equals("1"))
				{
					setConDj(true);
					flag_dj = "1";
				}
				
				String flag = papeleta.getFlag_pp();
				
				if (flag==null || flag.equals("0"))
				{
					setFlag_pp("Publico");
				}
				else
				{
					setFlag_pp("Privado");
				}					
				
				setLey(mapILey.get(papeleta.getLeyId()));
				if (getLey() != null && getLey().trim().length() > 0) {
					preparaInfracciones(getLey());
					setInfraccion(mapIInfraccion.get(papeleta.getInfraccionId()));
					setMontoSancion(papeleta.getMontoMulta());
				}
				// Obtener Datos del infractor
				if (papeleta.getPersonaInfractorId() != null) {
					//datosInfractor = papeletaBo.getPersonaPapeleta(papeleta.getPersonaInfractorId(),papeleta.getPapeletaId());
					datosInfractor=papeletaBo.getPersonaPapeleta(papeleta.getPersonaInfractorId(),papeleta.getPapeletaId(),Constante.FLAG_ACTIVO);//Correccion de flag es infractor a la tabla pa_direccion
					
					infractorOriginal = new BuscarPersonaDTO();
					infractorOriginal =datosInfractor;
					
				}

				// Datos del propietario
				if (papeleta.getPersonaPropietarioId() != null) {
					//datosPropietario = papeletaBo.getPersonaPapeleta(papeleta.getPersonaPropietarioId(),papeleta.getPapeletaId());
					datosPropietario=papeletaBo.getPersonaPapeleta(papeleta.getPersonaPropietarioId(),papeleta.getPapeletaId(),Constante.FLAG_INACTIVO);//Correccion de flag es infractor a la tabla pa_direccion
				}

				if (papeleta.getPersonaPropietarioId() != null
						&& papeleta.getPersonaPropietarioId().equals(papeleta.getPersonaInfractorId())) {
					setEsPropietario(Boolean.TRUE);
				} else {
					setEsPropietario(Boolean.FALSE);
				}

				// Datos del vehiculo
				if (papeleta.getPlaca().contains("Rematado")) {
					setNroPlaca(papeleta.getPlaca().replace("Rematado", ""));
					setDisableVehiculoRematado(true);
				} else {
					setNroPlaca(papeleta.getPlaca());
					setDisableVehiculoRematado(false);
				}
				setNumeroTarjetaPropiedad(papeleta.getNroTarjetaPropiedad());

				// setSinLicencia(papeleta.getSinLicencia().equals("N")?Boolean.FALSE:Boolean.TRUE);
				/**
				 * if(getSinLicencia()){ getTxtNumeroLicencia().setValue("");
				 * getCmbClase().setValue(""); }else{
				 * getTxtNumeroLicencia().setValue(papeleta.getNumLicencia());
				 * getCmbClase().setValue(mapIClaseLicencia.get(papeleta.getClaseLicenciaId())); }
				 */
				//loadImageFromBD(papeleta.getPapeletaId());
				//setImagePapeleta("\\satc\\tmp\\" + getNroPapeleta() + ".jpg");

				incidencia = papeletaBo.getIncidencia(papeleta.getPersonaInfractorId(),
						papeleta.getInfraccionId(),
						papeleta.getPapeletaId());
				if (incidencia == null) {
					incidencia = new PaIncidencia();
					incidencia.setIncidenciaId(Constante.RESULT_PENDING);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadImageFromBD(Integer papeletaId) {
		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectImage();

			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String path = ctx.getRealPath("/");

			String dtFile = path + "tmp" + File.separator + getNroPapeleta() + ".jpg";
			getImageData(connection, papeletaId, dtFile);
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		} finally {
			try {
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (Exception e) {
			}
		}
	}

	public void getImageData(Connection conn, Integer codAnteriorId, String fileName) {
		byte[] fileBytes;
		String query;
		try {
			query = "select imapap from bd_imagenes.dbo.t_papeleta_imagenes where papeleta_id=?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, codAnteriorId);

			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				fileBytes = rs.getBytes(1);
				OutputStream targetFile = new FileOutputStream(fileName);

				targetFile.write(fileBytes);
				targetFile.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertImageData(Connection conn, String img, Integer papeletaId) {
		int len;
		String query;
		PreparedStatement pstmt;

		try {
			File file = new File(img);
			FileInputStream fis = new FileInputStream(file);
			len = (int) file.length();

			query = ("INSERT INTO bd_imagenes.dbo.t_papeleta_imagenes(papeleta_id,imapap,fecima,fecreg,perarereg) VALUES(?,?,GETDATE(),GETDATE(),1)");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, papeletaId);
			// Method used to insert a stream of bytes
			pstmt.setBinaryStream(2, fis, len);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateImageData(Connection conn, String img, Integer papeletaId) {
		int len;
		String query;
		PreparedStatement pstmt;

		try {
			File file = new File(img);
			FileInputStream fis = new FileInputStream(file);
			len = (int) file.length();

			query = ("UPDATE bd_imagenes.dbo.t_papeleta_imagenes SET imapap=?,fecreg=GETDATE() WHERE papeleta_id=?");
			pstmt = conn.prepareStatement(query);
			// Method used to insert a stream of bytes
			pstmt.setBinaryStream(1, fis, len);
			pstmt.setInt(2, papeletaId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean poseeImagen(Connection conn, Integer papeletaId) {
		boolean exist = false;
		String query;
		PreparedStatement pstmt;
		try {
			query = (" SELECT count(*) as cantidad FROM bd_imagenes.dbo.t_papeleta_imagenes WHERE papeleta_id=?");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, papeletaId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("cantidad") > 0) {
					exist = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exist;
	}

	public void guardar() {
		try {
			if (validaDatosPapeleta()) {
				if (validaDatosInfractor()) {
					if (validaDatosPropietario()) {
						if (validaNumeroPapeleta()) {
							// if(validaSinLicencia()){
							// Registro de tablas mayores
							// Datos del vehiculo
							// Registra datos del infractor
							if (getDatosInfractor() != null && getDatosInfractor().getPersonaId() != null) {
								PaPersona infractor = getPaPersona(getDatosInfractor());
								
								//Integer Id = papeletaBo.guardarPaPersona(infractor);
								
								Integer Id = papeletaBo.guardarPaPersona(infractor,infractorOriginal,getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
								
								
								if (Id > Constante.RESULT_PENDING) {
									getDatosInfractor().setPersonaId(Id);
									papeleta.setPersonaInfractorId(Id);
								}
							}
							// Registra datos del propietario
							if (getDatosPropietario() != null && getDatosPropietario().getPersonaId() != null) {
								PaPersona propietario = getPaPersona(getDatosPropietario());
								
								Integer Id = papeletaBo.solo_guardarPaPersona(propietario);
								
								
								if (Id > Constante.RESULT_PENDING) {
									getDatosPropietario().setPersonaId(Id);
									papeleta.setPersonaPropietarioId(getDatosPropietario().getPersonaId());
								}
							} else {
								papeleta.setPersonaPropietarioId(null);
							}
							// Guardar datos generales de la papeleta
							if (getPapeletaId() != null) {
								papeleta.setPapeletaId(getPapeletaId());
							} else {
								papeleta.setPapeletaId(Constante.RESULT_PENDING);
							}
							papeleta.setNroPapeleta(getNroPapeleta());
							// if(getSinLicencia()){
							papeleta.setSinLicencia("S");
							papeleta.setNumLicencia(null);

							/**
							 * }else{ //papeleta.setSinLicencia("N");
							 * //papeleta.setNumLicencia(getTxtNumeroLicencia
							 * ().getValue().toString());
							 * 
							 * }
							 */
							/** Registrando la fecha de infraccion */

							papeleta.setFechaInfraccion(DateUtil.dateToSqlTimestamp(getFechaInfraccion()));
							papeleta.setHoraInfraccion(getHoraInfraccion());

							/**
							 * if(getCmbClase()!=null&&getCmbClase().getValue()!=null&&getCmbClase()
							 * .getValue().toString().trim().length()>0){
							 * papeleta.setClaseLicenciaId
							 * (mapClaseLicencia.get(getCmbClase().getValue())); }else{
							 */
							papeleta.setClaseLicenciaId(null);
							// }

							papeleta.setMontoMulta(getMontoSancion());

							papeleta.setLeyId(mapLey.get(getLey().trim()));
							papeleta.setInfraccionId(mapInfraccion.get(getInfraccion().trim()));
							// cc: papeleta.setUsuarioId(Constante.USUARIO_ID);
							// cc: papeleta.setTerminal(Constante.TERMINAL);
							// cc: papeleta.setFechaRegistro(DateUtil.getCurrentDate());

							papeleta.setEstado(getEstadoPapeleta());

							papeleta.setPlaca(getNroPlaca());
							papeleta.setPlacaAnterior(getPlacaAnterior());
							papeleta.setNroTarjetaPropiedad(getNumeroTarjetaPropiedad());

							if (!(papeleta.getOrigen() != null && papeleta.getOrigen()
									.equals(Constante.ORIGEN_PAPELETA_MASIVO))) {
								papeleta.setOrigen(Constante.ORIGEN_PAPELETA_INDIVIDUAL);
							}
							papeleta.setGlosa(glosa);
							//AQUIII
							
							//setFlag_pp("P�blico");
							
							//System.out.println("conDJ: "+conDj);
							
							papeleta.setFlag_declarante(flag_declarante);
							papeleta.setFlag_dj(flag_dj);
							
							if(getFlag_pp().equals("Publico"))
								papeleta.setFlag_pp("0");
							else
								papeleta.setFlag_pp("1");
							
							if (papeleta.getUsuarioId() == null)
							{
								papeleta.setUsuarioId(getUser().getUsuarioId());
							}
							
							if (papeleta.getTerminal() == null)
							{
								papeleta.setTerminal(getSessionManaged().getTerminalLogIn());
							}
							
							if (papeleta.getFechaRegistro() == null)
							{
								papeleta.setFechaRegistro(DateUtil.getCurrentDate());
							}
							
							/** Guardando/Actualizando la papeleta */
							
							if (papeleta.getUsuarioId() == null)
							{
								papeleta.setUsuarioId(getUser().getUsuarioId());
							}
							
							if (papeleta.getTerminal() == null)
							{
								papeleta.setTerminal(getSessionManaged().getTerminalLogIn());
							}
							
							if (papeleta.getFechaRegistro() == null)
							{
								papeleta.setFechaRegistro(DateUtil.getCurrentDate());
							}
							
							
							Integer papeletaId = papeletaBo.guardarPapeleta(papeleta);
							
							setPapeletaId(papeletaId);
							if (papeletaId > Constante.RESULT_PENDING) {
								// Solo papeletas guardadas correctamente
								papeleta.setPapeletaId(papeletaId);
								CalculoPapeleta calculo = new CalculoPapeleta(calculoPredialBo, papeletaBo);
								
								/*POr verificar y activar
								//Verificamos si la papeleta ha sido determinada
								Integer cantidadDeterminaciones=0;
								//Si es 0 no indica q aún no ha sido determinada la deuda.
								cantidadDeterminaciones=calculo.papeletaDeterminada(papeleta.getPapeletaId());
								
								//Verificamos si es necesario la determinación. Si la papaleta existe y no ha sido modificada la infracción o el año de infracción no es necesario Determinar nuevamente.							
								
								if ( cantidadDeterminaciones==0 || fechaInfraccionOriginal==null || fechaInfraccionOriginal.getYear() != papeleta.getFechaInfraccion().getYear() || infraccionIdOrigial==null || infraccionIdOrigial==0 || ! infraccionIdOrigial.equals(papeleta.getInfraccionId()) )
								{								
									calculo.generarDeterminacion(papeleta, incidencia);
								}
								*/
								
								calculo.generarDeterminacion(papeleta, incidencia);
								
								
								//Esto se comentó para que no guarde la imagen en binario
								/*
								ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
										.getExternalContext().getContext();
								String path = ctx.getRealPath("/");
								String dtFile = path + "tmp" + File.separator + getNroPapeleta() + ".jpg";
								// Correccion de flag es infractor a la tabla pa_direccion
								File f = new File(dtFile);
								if (f.exists()) {
									com.sat.sisat.persistence.CrudServiceBean connj = CrudServiceBean.getInstance();
									if (poseeImagen(connj.connectImage(), papeletaId)) {
										updateImageData(connj.connectImage(), dtFile, papeletaId);
									} else {
										insertImageData(connj.connectImage(), dtFile, papeletaId);
									}
								}
								*/
							}

							incidencia = papeletaBo.getIncidencia(papeleta.getPersonaInfractorId(),
									papeleta.getInfraccionId(),
									papeleta.getPapeletaId());
							if (incidencia == null) {
								incidencia = new PaIncidencia();
								incidencia.setIncidenciaId(Constante.RESULT_PENDING);
							} else {
								Integer puntosAcum = papeletaBo.getPuntosAcum(papeleta.getPersonaInfractorId(),
										papeleta.getFechaInfraccion(),
										CalculoPapeleta.PERIODO_PUNTOS_ACUM_MESES);
								incidencia.setPuntosAcum(puntosAcum != null ? puntosAcum : 0);
							}
							if (papeletaId > Constante.RESULT_PENDING) {
								// ORIGEN M:MASIVO / I:INDIVIDUAL
								// ESTADO 0:INACTIVO(PENDIENTE DE VERIFICACION) 1:ACTIVO 3:PAGADO
								// (DESCARGO) 9:ELIMINADO
								papeletaBo.guardarAdjuntoPapeleta(getPapeletaId(), recordsDocAnexos);

								// Eliminar las direcciones anteriores
								papeletaBo.eliminarEstadoPaDireccion(papeletaId);
								// GUardar direccion de persona en papeleta
								if (getDatosInfractor() != null && getDatosInfractor().getPersonaId() != null
										&& getDatosInfractor().getPersonaId() > 0) {
									PaDireccion direccion = getPaDireccion(getDatosInfractor());
									direccion.setPapeletaId(papeletaId);
									direccion.setEsInfractor(Constante.FLAG_ACTIVO);//Correccion de flag es infractor a la tabla pa_direccion
									direccion.setDireccionId(Constante.RESULT_PENDING);//Correccion de flag es infractor a la tabla pa_direccion
									Integer Id = papeletaBo.guardarPaDireccion(direccion);
									if (Id > Constante.RESULT_PENDING) {
										getDatosInfractor().setDireccionId(Id);
									}
								}

								// Registra datos del propietario
								if (getDatosPropietario() != null && getDatosPropietario().getPersonaId() != null
									&& getDatosPropietario().getPersonaId() > 0) {
									//Correccion de las direcciones del propietario
									PaDireccion direccion = getPaDireccion(getDatosPropietario());
									direccion.setPapeletaId(papeletaId);
									direccion.setEsInfractor(Constante.FLAG_INACTIVO);//Correccion de flag es infractor a la tabla pa_direccion
									direccion.setDireccionId(Constante.RESULT_PENDING);//Correccion de flag es infractor a la tabla pa_direccion
									Integer Id = papeletaBo.guardarPaDireccion(direccion);
									if (Id > Constante.RESULT_PENDING) {
										getDatosPropietario().setDireccionId(Id);
									}
								}
							}

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
		//temporal para almacenar la infracción y fecha de registro antes de cualquier cambio. Tal como fue recuperada de la BD
		infraccionIdOrigial=papeleta.getInfraccionId();
		fechaInfraccionOriginal=papeleta.getFechaInfraccion();				

		
	}
	
	public void exportarDj() {
		java.sql.Connection connection=null;
        try {
        	
        		CrudServiceBean connj=CrudServiceBean.getInstance();
   			    connection=connj.connectJasper(); 
        		
        		  HashMap<String,Object> parameters = new HashMap<String,Object>();
        		  parameters.put("nro_papeleta", nroPapeleta);	   			    			  
	   			  parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
	   			  parameters.put("username", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	   			  parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
	   			  //--
	   			  JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"report_dj_papeleta.jasper"), parameters, connection);
	   		      ByteArrayOutputStream output = new ByteArrayOutputStream();
	   		      
	   		      JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	   			  HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	   			  response.setContentType("application/pdf");
	   			  response.addHeader("Content-Disposition","attachment;filename=dj_"+nroPapeleta+".pdf");
	   			  response.setContentLength(output.size());
	   			  ServletOutputStream servletOutputStream = response.getOutputStream();
	   			  servletOutputStream.write(output.toByteArray(), 0, output.size());
	   			  servletOutputStream.flush();
	   			  servletOutputStream.close();
	   			  FacesContext.getCurrentInstance().responseComplete();
        	
			 
	     }catch (Exception jre) {
	          jre.printStackTrace();
	          WebMessages.messageFatal(jre);
	     }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
    }
	
	public void previewDJ(){
		
	}
	
	
	public PaPersona getPaPersona(BuscarPersonaDTO persona) {
		
		PaPersona paPersona = new PaPersona();
		paPersona.setPersonaId(persona.getPersonaId());
		paPersona.setTipoDocumentoId(persona.getTipodocumentoIdentidadId());
		paPersona.setNroDocIdentidad(persona.getNroDocuIdentidad());
		
		
		
		if (persona.getTipodocumentoIdentidadId() == Constante.TIPO_DOCUMENTO_RUC_ID) {
			paPersona.setRazonSocial(persona.getRazonSocial());
		} else {
			paPersona.setPrimerNombre(persona.getPrimerNombre());
			paPersona.setSegundoNombre(persona.getSegundoNombre());
			paPersona.setApePaterno(persona.getApellidoPaterno());
			paPersona.setApeMaterno(persona.getApellidoMaterno());
		}
		paPersona.setClaseLicenciaId(persona.getClaseLicenciaId());
		paPersona.setNumLicencia(persona.getNumLicencia());
		paPersona.setEstado(Constante.ESTADO_ACTIVO);
		persona.setFechaRegistro(persona.getFechaRegistro());
		
		paPersona.setTerminalRegistro(persona.getTerminal());
		
		return paPersona;
	}

	public PaDireccion getPaDireccion(BuscarPersonaDTO persona) {
		PaDireccion paDireccion = new PaDireccion();
		paDireccion.setDireccionCompleta(persona.getDireccionCompleta());
		paDireccion.setDireccionId(persona.getDireccionId());
		paDireccion.setTipoViaId(persona.getTipoViaId());
		paDireccion.setViaId(persona.getViaId());
		// paDireccion.setLugarId(persona.getLugarId());
		paDireccion.setNumero(persona.getNumero());
		paDireccion.setEstado(Constante.ESTADO_ACTIVO);
		paDireccion.setPersonaId(persona.getPersonaId());
		
		return paDireccion;
	}

	public Boolean validaDatosInfractor() {
		if (!(getDatosInfractor() != null && getDatosInfractor().getPersonaId() != null && validaApellidosNombres(getDatosInfractor()))) {
			WebMessages.messageError("Registre los datos del infractor");
			return Boolean.FALSE;
		}

		if (getDatosInfractor() != null && getDatosInfractor().getDireccionId() != null) {
			if (getDatosInfractor().getTipoViaId() != null
					&& getDatosInfractor().getTipoViaId() > Constante.RESULT_PENDING
					&& getDatosInfractor().getViaId() != null
					&& getDatosInfractor().getViaId() > Constante.RESULT_PENDING
					&& getDatosInfractor().getNumero() != null && getDatosInfractor().getNumero().trim().length() > 0) {
				;
			} else if (getDatosInfractor().getDireccionCompleta() != null
					&& getDatosInfractor().getDireccionCompleta().trim().length() > 0) {
				;
			} else {
				WebMessages.messageError("Registre la direccion del infractor");
				return Boolean.FALSE;
			}
		} else {
			WebMessages.messageError("Registre la direccion del infractor");
			return Boolean.FALSE;
		}

		if (getDatosInfractor().getTipodocumentoIdentidadId() != null
				&& getDatosInfractor().getTipodocumentoIdentidadId() > 0
				&& getDatosInfractor().getNroDocuIdentidad() != null
				&& !getDatosInfractor().getNroDocuIdentidad().isEmpty()
				&& getDatosInfractor().getNroDocuIdentidad().trim().length() > 0) {
			;
		} else {
			WebMessages.messageError("Registre el documento de identidad del infractor");
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	public Boolean validaDatosPropietario() {
		if (getDatosPropietario() != null && getDatosPropietario().getPersonaId() != null) {

			if (!(getDatosPropietario() != null && getDatosPropietario().getPersonaId() != null && validaApellidosNombresRazSoc(getDatosPropietario()))) {
				WebMessages.messageError("Registre los datos del propietario");
				return Boolean.FALSE;
			}

			if (getDatosPropietario() != null && getDatosPropietario().getDireccionId() != null) {
				if (getDatosPropietario().getTipoViaId() != null
						&& getDatosPropietario().getTipoViaId() > Constante.RESULT_PENDING
						&& getDatosPropietario().getViaId() != null
						&& getDatosPropietario().getViaId() > Constante.RESULT_PENDING
						&& getDatosPropietario().getNumero() != null
						&& getDatosPropietario().getNumero().trim().length() > 0) {
					;
				} else if (getDatosPropietario().getDireccionCompleta() != null
						&& getDatosPropietario().getDireccionCompleta().trim().length() > 0) {
					;
				} else {
					WebMessages.messageError("Registre la direccion del propietario");
					return Boolean.FALSE;
				}
			} else {
				WebMessages.messageError("Registre la direccion del propietario");
				return Boolean.FALSE;
			}

			if (getDatosPropietario().getTipodocumentoIdentidadId() != null
					&& getDatosPropietario().getTipodocumentoIdentidadId() > 0
					&& getDatosPropietario().getNroDocuIdentidad() != null
					&& !getDatosPropietario().getNroDocuIdentidad().isEmpty()
					&& getDatosPropietario().getNroDocuIdentidad().trim().length() > 0) {
				;
			} else {
				WebMessages.messageError("Registre el documento de identidad del propietario");
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	public boolean validaApellidosNombres(BuscarPersonaDTO persona) {
		if (persona.getPrimerNombre() != null && ValidateInput.validateFirstName(persona.getPrimerNombre().trim())
				&& persona.getApellidoPaterno() != null
				&& ValidateInput.validateLastName(persona.getApellidoPaterno().trim())
				&& persona.getApellidoMaterno() != null
				&& ValidateInput.validateLastName(persona.getApellidoMaterno().trim())) {

			if (persona.getSegundoNombre() != null && persona.getSegundoNombre().trim().length() > 0) {
				if (ValidateInput.validateFirstName(persona.getPrimerNombre().trim())) {
//					if (ValidateInput.validateFirstName(persona.getSegundoNombre().trim())) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}

		} else {
			return false;
		}
	}

	public boolean validaApellidosNombresRazSoc(BuscarPersonaDTO persona) {
		if (persona.getTipodocumentoIdentidadId() != null && persona.getTipodocumentoIdentidadId() == 2) {
			if (persona.getRazonSocial() != null && !persona.getRazonSocial().isEmpty()
					&& persona.getRazonSocial().trim().length() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (persona.getPrimerNombre() != null && ValidateInput.validateFirstName(persona.getPrimerNombre().trim())
					&& persona.getApellidoPaterno() != null
					&& ValidateInput.validateLastName(persona.getApellidoPaterno().trim())
					&& persona.getApellidoMaterno() != null
					&& ValidateInput.validateLastName(persona.getApellidoMaterno().trim())) {
				if (persona.getSegundoNombre() != null && persona.getSegundoNombre().trim().length() > 0) {
					if (ValidateInput.validateFirstName(persona.getPrimerNombre().trim())) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
	}

	public Boolean validaDatosPapeleta() {
		Calendar now = Calendar.getInstance();
		Calendar fechaInfraccion = Calendar.getInstance();
		fechaInfraccion.setTime(getFechaInfraccion());
		if (DateUtil.diferenciaFechas(fechaInfraccion, now, 1) < 0) {
			WebMessages.messageError("La fecha de infraccion es invalida");
			return false;
		}
		return true;
	}

	public void download() {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			RepositoryManager.buscarContenido(String.valueOf(paDocuAnexo.getContentId()), output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType(Constantes.CONTENT_MIMETYPE_JPEG);
			response.addHeader("Content-Disposition", "attachment;filename=" + paDocuAnexo.getReferencia());
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEstadoPapeleta() {
		String estado = (String) getSessionMap().get("estadoPapeleta");
		if (estado != null) {
			return estado;
		} else {
			return Constante.ESTADO_PAPELETA_DEFINITIVO;
		}
	}

	public String getEstadoPapeletaDesc() {
		String estadoPapeleta = (String) getSessionMap().get("estadoPapeleta");
		if (estadoPapeleta != null) {
			if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_PENDIENTE)) {
				return "verificacion";
			} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_REGISTRADO)) {
				return "Registrado";
			} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_NO_COINCIDE)) {
				return "No Coincide";
			} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_DEFINITIVO)) {
				return "Pendiente";
			}
			// agregado
			else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_CANCELADO)) {
				return "Cancelado";
			} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_DESCARGADO)) {
				return "Descargado";
			} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_ANULADO)) {
				return "Anulado";
			} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_COMPENSADO)) {
				return "Compensado";
			} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_PRESCRITO)) {
				return "Prescrito";
			}
			return estadoPapeleta;
		} else {
			return "Registrado";
		}
	}

	public Boolean validaNumeroPapeleta() {
		Boolean result = Boolean.FALSE;
		try {

			Calendar fechaInfraccion = Calendar.getInstance();
			fechaInfraccion.setTime(getFechaInfraccion());

			PaPapeleta papeleta = papeletaBo.getPaPapeletaByNumeroPapeleta(getNroPapeleta().trim(),
					getPapeletaId() == null ? Constante.RESULT_PENDING : getPapeletaId(),
					fechaInfraccion.get(Calendar.YEAR));
			if (papeleta != null) {
				WebMessages.messageError("El numero de papeleta : " + papeleta.getNroPapeleta()
						+ " ya se encuentra registrado con fecha de infraccion : "
						+ DateUtil.convertDateToString(papeleta.getFechaInfraccion()));
				result = Boolean.FALSE;
			} else {
				result = Boolean.TRUE;
			}
		} catch (Exception e) {
			result = Boolean.FALSE;
		}
		return result;
	}

	public Boolean validaSinLicencia() {
		// if(!getSinLicencia()){
		/*
		 * if(getTxtNumeroLicencia()!=null&&getTxtNumeroLicencia().getValue().toString().trim().length
		 * ()>0){ return Boolean.TRUE; }else{
		 * WebMessages.messageError("Ingrese el Numero de Licencia de conducir"); return
		 * Boolean.FALSE; }
		 */
		// }else{
		return Boolean.TRUE;
		// }
	}

	public void changeOptionSinLicencia(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			getTxtNumeroLicencia().setValue("");
			getCmbClase().setValue("");
			setSinLicencia(Boolean.TRUE);
		} else {
			setSinLicencia(Boolean.FALSE);
		}
	}

	public void cargaInfracciones(ValueChangeEvent e) {
		try {
			Date localeCode = (Date) e.getNewValue();
			if (localeCode != null) {
				 setFechaInfraccionTramos(localeCode);
			}
			preparaInfracciones(getLey());
		} catch (Exception exc) {
			exc.printStackTrace();
			WebMessages.messageFatal(exc);
		}
	}
		
	public void preparaInfracciones(String descLey) {
		try {
			if (descLey != null && !descLey.isEmpty()) {
				setLey(descLey);
				Integer codLey = mapLey.get(descLey);
				PaInfraccion infraccion = new PaInfraccion();
				infraccion.setLeyId(codLey);
				
				//System.out.println(getFechaInfraccionTramos());
				
				List<PaInfraccion> lstPaInfraccion = papeletaBo.getAllInfracciones(infraccion,DateUtil.convertDateToString(getFechaInfraccionTramos()));
//				List<PaInfraccion> lstPaInfraccion = papeletaBo.getAllInfracciones(infraccion);
				Iterator<PaInfraccion> it8 = lstPaInfraccion.iterator();
				lstInfraccion = new ArrayList<SelectItem>();
				while (it8.hasNext()) {
					PaInfraccion obj = it8.next();
					lstInfraccion.add(new SelectItem(obj.getDescripcionCorta(), String.valueOf(obj.getInfraccionId())));
					mapInfraccion.put(obj.getDescripcionCorta().trim(), obj.getInfraccionId());
					mapIInfraccion.put(obj.getInfraccionId(), obj.getDescripcionCorta().trim());
					mapPaInfraccion.put(obj.getDescripcionCorta().trim(), obj);
				}
			} else {
				setInfraccion("");
				lstInfraccion = new ArrayList<SelectItem>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAnexoProbatorio() {
		try {
			if (paDocuAnexo != null) {
				recordsDocAnexos.remove(paDocuAnexo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void nuevoDocumentoAnexo() {

	}

	public void addDocumentosAnexos(PaDocuAnexo documento) {
		try {
			recordsDocAnexos.add(documento);
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void checkConDj(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setFlag_dj("1");
			conDj=Boolean.TRUE;
			
		} else {
			setFlag_dj("0");
			conDj=Boolean.FALSE;
		}
	}
	
	public void checkDeclarante(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			flag_declarante = "P";
		} else {
			flag_declarante = "C";
		}
	}
	
	public void checkEsPropietario(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			//Correccion de las direcciones del propietario
			Integer direccionPropietarioId=Constante.RESULT_PENDING;
			if(getDatosPropietario()!=null&&getDatosPropietario().getDireccionId()!=null&&getDatosPropietario().getDireccionId()>Constante.RESULT_PENDING){
				direccionPropietarioId=getDatosPropietario().getDireccionId();
			}
			setDatosPropietario(getDatosInfractor());
			getDatosPropietario().setDireccionId(direccionPropietarioId);
			setEsPropietario(Boolean.TRUE);
		} else {
			setEsPropietario(Boolean.FALSE);
			setDatosPropietario(null);
		}
	}

	public void limpiarPropietario() {
		setDatosPropietario(new BuscarPersonaDTO());
	}
	
	public void actualizarPersona() throws Exception {
		String personaPapeleta = FacesUtil.getRequestParameter("personaPapeleta");

		if (personaPapeleta != null && personaPapeleta.equals("I")) {
			ActualizaPersonasPapeletasManaged registro = (ActualizaPersonasPapeletasManaged) getManaged("actualizaPersonasPapeletasManaged");
			registro.setProperty(datosInfractor);
			registro.setActualizaPersona("S");
			esContribuyente= papeletaBo.getInfractorContribuyente(registro.getPersonaId());
			getSessionMap().put("esContribuyente", esContribuyente);
			getSessionMap().put("personaPapeleta", personaPapeleta);
		}

		if (personaPapeleta != null && personaPapeleta.equals("P")) {
			ActualizaPersonasPapeletasManaged registro = (ActualizaPersonasPapeletasManaged) getManaged("actualizaPersonasPapeletasManaged");
			registro.setProperty(datosPropietario);
			registro.setActualizaPersona("S");
			esContribuyente= papeletaBo.getInfractorContribuyente(registro.getPersonaId());
			getSessionMap().put("esContribuyente", esContribuyente);
			getSessionMap().put("personaPapeleta", personaPapeleta);
		}
	}

	public void registrarPersona() {
		String personaPapeleta = FacesUtil.getRequestParameter("personaPapeleta");

		if (personaPapeleta != null && personaPapeleta.equals("I")) {
			ActualizaPersonasPapeletasManaged registro = (ActualizaPersonasPapeletasManaged) getManaged("actualizaPersonasPapeletasManaged");
			registro.setProperty(new BuscarPersonaDTO());
			registro.setActualizaPersona("N");
			getSessionMap().put("personaPapeleta", personaPapeleta);
		}

		if (personaPapeleta != null && personaPapeleta.equals("P")) {
			ActualizaPersonasPapeletasManaged registro = (ActualizaPersonasPapeletasManaged) getManaged("actualizaPersonasPapeletasManaged");
			registro.setProperty(new BuscarPersonaDTO());
			registro.setActualizaPersona("N");
			getSessionMap().put("personaPapeleta", personaPapeleta);
		}
	}

	public BuscarPersonaDTO getDatosInfractor() {
		return datosInfractor;
	}
	
	public void redirectCargaCostas(){		
			
		getSessionMap().put("paPapeleta", papeleta);		
		this.getSessionManaged().setPage("/sisat/obligaciones/agregarObligacionPapeletas.xhtml");		
		
	}
	
	public void loadSeleccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			this.setFlag_pp(value);

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	/**
	 * Desde ActualizarPersonaPapeletasManaged
	 * 
	 * @param datosInfractor
	 */
	public void setDatosInfractor(BuscarPersonaDTO datosInfractor) {
		this.datosInfractor = datosInfractor;
	}

	public BuscarPersonaDTO getDatosPropietario() {
		return datosPropietario;
	}

	public void setDatosPropietario(BuscarPersonaDTO datosPropietario) {
		this.datosPropietario = datosPropietario;
	}

	public PapeletaBoRemote getPapeletaBo() {
		return papeletaBo;
	}

	public void setPapeletaBo(PapeletaBoRemote papeletaBo) {
		this.papeletaBo = papeletaBo;
	}

	public List<SelectItem> getLstClaseLicencia() {
		return lstClaseLicencia;
	}

	public void setLstClaseLicencia(List<SelectItem> lstClaseLicencia) {
		this.lstClaseLicencia = lstClaseLicencia;
	}

	public Map<String, Integer> getMapClaseLicencia() {
		return mapClaseLicencia;
	}

	public void setMapClaseLicencia(Map<String, Integer> mapClaseLicencia) {
		this.mapClaseLicencia = mapClaseLicencia;
	}

	public Map<Integer, String> getMapIClaseLicencia() {
		return mapIClaseLicencia;
	}

	public void setMapIClaseLicencia(Map<Integer, String> mapIClaseLicencia) {
		this.mapIClaseLicencia = mapIClaseLicencia;
	}

	public List<SelectItem> getLstLey() {
		return lstLey;
	}

	public void setLstLey(List<SelectItem> lstLey) {
		this.lstLey = lstLey;
	}

	public Map<String, Integer> getMapLey() {
		return mapLey;
	}

	public void setMapLey(Map<String, Integer> mapLey) {
		this.mapLey = mapLey;
	}

	public Map<Integer, String> getMapILey() {
		return mapILey;
	}

	public void setMapILey(Map<Integer, String> mapILey) {
		this.mapILey = mapILey;
	}

	public List<SelectItem> getLstInfraccion() {
		return lstInfraccion;
	}

	public void setLstInfraccion(List<SelectItem> lstInfraccion) {
		this.lstInfraccion = lstInfraccion;
	}

	public Map<String, Integer> getMapInfraccion() {
		return mapInfraccion;
	}

	public void setMapInfraccion(Map<String, Integer> mapInfraccion) {
		this.mapInfraccion = mapInfraccion;
	}

	public Map<Integer, String> getMapIInfraccion() {
		return mapIInfraccion;
	}

	public void setMapIInfraccion(Map<Integer, String> mapIInfraccion) {
		this.mapIInfraccion = mapIInfraccion;
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

	public Boolean getSinLicencia() {
		return sinLicencia;
	}

	public void setSinLicencia(Boolean sinLicencia) {
		this.sinLicencia = sinLicencia;
	}

	public String getNumeroTarjetaPropiedad() {
		return numeroTarjetaPropiedad;
	}

	public void setNumeroTarjetaPropiedad(String numeroTarjetaPropiedad) {
		this.numeroTarjetaPropiedad = numeroTarjetaPropiedad;
	}

	public void setFechaInfraccion(Timestamp fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}

	public String getHoraInfraccion() {
		return horaInfraccion;
	}

	public void setHoraInfraccion(String horaInfraccion) {
		this.horaInfraccion = horaInfraccion;
	}

	public Double getMontoSancion() {
		return montoSancion;
	}

	public void setMontoSancion(Double montoSancion) {
		this.montoSancion = montoSancion;
	}

	public ArrayList<PaDocuAnexo> getRecordsDocAnexos() {
		return recordsDocAnexos;
	}

	public void setRecordsDocAnexos(ArrayList<PaDocuAnexo> recordsDocAnexos) {
		this.recordsDocAnexos = recordsDocAnexos;
	}

	public Boolean getEsPropietario() {
		return esPropietario;
	}

	public void setEsPropietario(Boolean esPropietario) {
		this.esPropietario = esPropietario;
	}

	public String getLey() {
		return ley;
	}

	public void setLey(String ley) {
		this.ley = ley;
	}

	public String getInfraccion() {
		return infraccion;
	}

	public void setInfraccion(String infraccion) {
		this.infraccion = infraccion;
	}

	public PaDocuAnexo getPaDocuAnexo() {
		return paDocuAnexo;
	}

	public void setPaDocuAnexo(PaDocuAnexo paDocuAnexo) {
		this.paDocuAnexo = paDocuAnexo;
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

	public String getPageReturn() {
		return pageReturn;
	}

	public void setPageReturn(String pageReturn) {
		this.pageReturn = pageReturn;
	}

	public String getImagePapeleta() {
		return imagePapeleta;
	}

	public void setImagePapeleta(String imagePapeleta) {
		this.imagePapeleta = imagePapeleta;
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

	public PaDireccion getPaDireccion() {
		return paDireccion;
	}

	public void setPaDireccion(PaDireccion paDireccion) {
		this.paDireccion = paDireccion;
	}

	public HtmlInputHidden getTxtNumeroLicencia() {
		return txtNumeroLicencia;
	}

	public void setTxtNumeroLicencia(HtmlInputHidden txtNumeroLicencia) {
		this.txtNumeroLicencia = txtNumeroLicencia;
	}

	public HtmlComboBox getCmbClase() {
		return cmbClase;
	}

	public void setCmbClase(HtmlComboBox cmbClase) {
		this.cmbClase = cmbClase;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getPlacaAnterior() {
		return placaAnterior;
	}

	public void setPlacaAnterior(String placaAnterior) {
		this.placaAnterior = placaAnterior;
	}

	public Boolean getEsContribuyente() {
		return esContribuyente;
	}

	public void setEsContribuyente(Boolean esContribuyente) {
		this.esContribuyente = esContribuyente;
	}

	public java.util.Date getFechaInfraccionTramos() {
		return fechaInfraccionTramos;
	}

	public void setFechaInfraccionTramos(java.util.Date fechaInfraccionTramos) {
		this.fechaInfraccionTramos = fechaInfraccionTramos;
	}

	public Integer getTipoTransporte() {
		return tipoTransporte;
	}

	public void setTipoTransporte(Integer tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}

	public String getFlag_pp() {
		return flag_pp;
	}

	public void setFlag_pp(String flag_pp) {
		this.flag_pp = flag_pp;
	}

	public HtmlComboBox getCmbHtmlTipoServicio() {
		return cmbHtmlTipoServicio;
	}

	public void setCmbHtmlTipoServicio(HtmlComboBox cmbHtmlTipoServicio) {
		this.cmbHtmlTipoServicio = cmbHtmlTipoServicio;
	}

	public Boolean getConDj() {
		return conDj;
	}

	public void setConDj(Boolean conDj) {
		this.conDj = conDj;
	}

	public Boolean getDeclarante() {
		return declarante;
	}

	public void setDeclarante(Boolean declarante) {
		this.declarante = declarante;
	}

	public String getFlag_dj() {
		return flag_dj;
	}

	public void setFlag_dj(String flag_dj) {
		this.flag_dj = flag_dj;
	}

	public String getFlag_declarante() {
		return flag_declarante;
	}

	public void setFlag_declarante(String flag_declarante) {
		this.flag_declarante = flag_declarante;
	}

	public Boolean getDisableVehiculoRematado() {
		return disableVehiculoRematado;
	}

	public void setDisableVehiculoRematado(Boolean disableVehiculoRematado) {
		this.disableVehiculoRematado = disableVehiculoRematado;
	}

}
