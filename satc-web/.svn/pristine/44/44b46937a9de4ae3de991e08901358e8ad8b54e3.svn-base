package com.sat.sisat.consumoWS;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;

import com.sat.sisat.administracion.business.AdministracionBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisatc.seguridad.dto.ConsultaReniecDTO;
import com.sat.sisatc.seguridad.dto.ConsultaSunarpDTO;

import pe.gob.reniec.ws.ResultadoConsulta;
import pe.gob.sunarp.pide.controller.AsientosBean;
import pe.gob.sunarp.pide.controller.FichaBean;
import pe.gob.sunarp.pide.controller.NumeroPaginasBean;
import pe.gob.sunarp.pide.controller.PIDEWSService;
import pe.gob.sunarp.pide.controller.PideService;
import pe.gob.sunarp.pide.controller.PropietarioBean;
import pe.gob.sunarp.pide.controller.RespuestaPartidaBean;
import pe.gob.sunarp.pide.controller.RespuestaTitularidad;
import pe.gob.sunarp.pide.controller.RespuestaVehiculoBean;
import pe.gob.sunarp.pide.controller.ResultadoTitularidad;
import pe.gob.sunarp.pide.controller.TomoFolioBean;

@ManagedBean
@ViewScoped

public class ConsultaSunarpManaged extends BaseManaged {

	@EJB
	AdministracionBoRemote administracionBo;

	private BuscarCodigoSunarp buscarOficinas = new BuscarCodigoSunarp();
	
	private RespuestaVehiculoBean respuestaVehiculo=new RespuestaVehiculoBean ();

	private ArrayList<OficinaSunarpDTO> oficinas = new ArrayList<OficinaSunarpDTO>();
	private OficinaSunarpDTO oficina = new OficinaSunarpDTO();

	private ArrayList<TipoRegistroSunarpDTO> tiposRegistro = new ArrayList<TipoRegistroSunarpDTO>();
	
	private String numeroPartida;
	private String oficinaSelect;
	private String tipoRegistroSelect;
	
	private String placa;
	

	private ArrayList<ResultadoTitularidad> rsultadoTitularidad = new ArrayList<ResultadoTitularidad>();

	private RespuestaPartidaBean resultadoAsientos = new RespuestaPartidaBean();
	private ResultadoTitularidad titularidad = new ResultadoTitularidad();
	private AsientosBean resultadoAsiento = new AsientosBean();
	private NumeroPaginasBean resultadoNumeroPagina = new NumeroPaginasBean();
	private FichaBean resuladoFicha = new FichaBean();
	private NumeroPaginasBean resultadoNumeroPaginaFicha = new NumeroPaginasBean();

	private TomoFolioBean resuladoFolio = new TomoFolioBean();

	private String rutaImagenAsiento = "";

	private Integer tipoPersona = 1;
	private String apellidoPaterno = "";
	private String apellidoMaterno = "";
	private String nombres = "";

	private String razonSocial = "";

	private String dniConsultaReniec;
	private String RUCConsultasSunat;

	private String rutaFotoReniec = "";

	private ResultadoConsulta resultadoConsultaReniec = new ResultadoConsulta();

	@PostConstruct
	public void init() throws Exception {
		try {
			resultadoAsientos = new RespuestaPartidaBean();
			titularidad = new ResultadoTitularidad();
			resultadoAsiento = new AsientosBean();
			resultadoNumeroPagina = new NumeroPaginasBean();
			resuladoFicha = new FichaBean();
			resultadoNumeroPaginaFicha = new NumeroPaginasBean();
			resuladoFolio = new TomoFolioBean();
			buscarOficinas = new BuscarCodigoSunarp();

			oficinas = buscarOficinas.getOficinas();
			tiposRegistro = buscarOficinas.getTipoRegsitros();
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void consultaReniec() {
		ConsultaReniecManaged consultaReniec = new ConsultaReniecManaged();
		resultadoConsultaReniec = new ResultadoConsulta();

		try {
			// Consulta WebService.
			consultaReniec.setDniConsulta(dniConsultaReniec);
			resultadoConsultaReniec = consultaReniec.consultaWS();

			// Necesitamos registrar los datos de auditoria
			ConsultaReniecDTO datos = new ConsultaReniecDTO();

			datos.setDniConsulta(getSessionManaged().getUsuarioLogIn().getDniUsuario());
			datos.setDniConsultado(consultaReniec.getDniConsulta());
			datos.setUsuarioID(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			datos.setTerminal(getSessionManaged().getTerminalLogIn());

			administracionBo.registrarConsultaReniec(datos);
			// Fin de registro de auditoria.

			rutaFotoReniec = consultaReniec.getRutaImagen();

			nombres = resultadoConsultaReniec.getDatosPersona().getPrenombres();
			apellidoPaterno = resultadoConsultaReniec.getDatosPersona().getApPrimer();
			apellidoMaterno = resultadoConsultaReniec.getDatosPersona().getApSegundo();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
		}

	}

	public void limpiarAsientos() {
		resultadoAsientos = new RespuestaPartidaBean();
		resultadoAsiento = new AsientosBean();
		resultadoNumeroPagina = new NumeroPaginasBean();
		resuladoFicha = new FichaBean();
		resultadoNumeroPaginaFicha = new NumeroPaginasBean();
		resuladoFolio = new TomoFolioBean();

	}

	public void limpiarAll() {
		try {
			resultadoAsientos = new RespuestaPartidaBean();
			resultadoAsiento = new AsientosBean();
			resultadoNumeroPagina = new NumeroPaginasBean();
			resuladoFicha = new FichaBean();
			resultadoNumeroPaginaFicha = new NumeroPaginasBean();
			resuladoFolio = new TomoFolioBean();

			rsultadoTitularidad.clear();
			titularidad = new ResultadoTitularidad();
			apellidoPaterno = "";
			apellidoMaterno = "";
			nombres = "";
			razonSocial = "";

			dniConsultaReniec = "";
			RUCConsultasSunat = "";
			rutaFotoReniec = "";
		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	public void consultaTitularidad() throws Exception {

		resultadoAsientos = new RespuestaPartidaBean();
		titularidad = new ResultadoTitularidad();
		resultadoAsiento = new AsientosBean();
		resultadoNumeroPagina = new NumeroPaginasBean();
		resuladoFicha = new FichaBean();
		resultadoNumeroPaginaFicha = new NumeroPaginasBean();
		resuladoFolio = new TomoFolioBean();

		apellidoPaterno = apellidoPaterno.toUpperCase();
		apellidoMaterno = apellidoMaterno.toUpperCase();
		nombres = nombres.toUpperCase();
		razonSocial = razonSocial.toUpperCase();

		String tipoParticipante;

		if (tipoPersona == 1) {
			// Persona Natural
			tipoParticipante = "N";
			razonSocial = "";

			// La bpsuqeuda es por Apellidos y Nombres
			// validamos
			if (apellidoPaterno.trim().isEmpty() || apellidoPaterno == null) {
				WebMessages.messageError("Debe Ingresar el Apellido Paterno.");
				return;
			}

			if (apellidoMaterno.trim().isEmpty() || apellidoMaterno == null) {
				WebMessages.messageError("Debe Ingresar el Apellido Materno.");
				return;
			}

			if (nombres.trim().isEmpty() || nombres == null) {
				WebMessages.messageError("Debe Ingresar el Nombre(s)");
				return;

			}

		} else {
			// Persona JJurídica.
			tipoParticipante = "J";

			if (razonSocial.trim().isEmpty() || razonSocial == null) {
				WebMessages.messageError("Debe Ingresar la Razón Social");
				return;
			}
		}

		PideService service1 = new PideService();
		RespuestaTitularidad respuestaTitularidad = new RespuestaTitularidad();

		try {
			PIDEWSService port2 = service1.getPIDEWSServicePort();
			respuestaTitularidad = new RespuestaTitularidad();
			respuestaTitularidad = port2.buscarTitularidad(tipoParticipante, apellidoPaterno, apellidoMaterno, nombres,
					razonSocial);

			// respuestaTitularidad.getRespuestaTitularidad().get(0).getMensaje();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// WebMessages.messageError("SUNARP no rsponde.");
			// return;
		} finally {
			// TODO: handle finally clause
		}

		rsultadoTitularidad.clear();

		if (respuestaTitularidad.getRespuestaTitularidad() == null
				|| respuestaTitularidad.getRespuestaTitularidad().size() == 0) {
			WebMessages.messageError("No registra Propiedades en SUNARP");
			return;
		}

		for (ResultadoTitularidad a : respuestaTitularidad.getRespuestaTitularidad()) {
			rsultadoTitularidad.add(a);
		}

		// Registramos auditoría.
		ConsultaSunarpDTO datos = new ConsultaSunarpDTO();

		if (tipoPersona == 1) // PN
		{
			datos.setApePaterno(apellidoPaterno);
			datos.setApeMaterno(apellidoMaterno);
			datos.setNombres(nombres);
		} else // PJ
			datos.setRazonSocial(razonSocial);

		datos.setUsuarioID(getSessionManaged().getUsuarioLogIn().getUsuarioId());
		datos.setTerminal(getSessionManaged().getTerminalLogIn());

		administracionBo.registrarConsultaSunarp(datos);

		// Fin de Registro de auditoría

	}

	public void verFolio() {
		descargarFoto(resultadoAsientos.getTransaccion(), resuladoFolio.getIdImgFolio().longValue(),
				resuladoFolio.getTipo().trim(), resuladoFolio.getPagina().trim(), resuladoFolio.getNroPagRef(),
				resuladoFolio.getPagina().trim());
	}

	public void verFicha() {
		descargarFoto(resultadoAsientos.getTransaccion(), resuladoFicha.getIdImgFicha().longValue(),
				resuladoFicha.getTipo().trim(), resuladoFicha.getNumPag().toString().trim(),
				resultadoNumeroPaginaFicha.getNroPagRef().trim(), resultadoNumeroPaginaFicha.getPagina().trim());

	}

	public void verAsiento() {
		descargarFoto(resultadoAsientos.getTransaccion(), resultadoAsiento.getIdImgAsiento().longValue(),
				resultadoAsiento.getTipo().trim(), resultadoAsientos.getNroTotalPag().trim(),
				resultadoNumeroPagina.getNroPagRef(), resultadoNumeroPagina.getPagina().trim());

	}

	public void descargarFoto(Long transaccion, Long idImg, String tipo, String nroTotalPag, String nroPagRef,
			String pagina) {

		PideService service1 = new PideService();
		PIDEWSService port1 = service1.getPIDEWSServicePort();

		try {
			byte[] fotoAsiento = null;

			fotoAsiento = port1.verAsiento(transaccion, idImg, tipo, nroTotalPag, nroPagRef, pagina);
			// fotoAsiento=port1.verAsiento(resultadoAsientos.getTransaccion(),
			// resultadoAsiento.getIdImgAsiento().longValue(),
			// resultadoAsiento.getTipo().trim(), resultadoAsientos.getNroTotalPag().trim(),
			// resultadoNumeroPagina.getNroPagRef(),resultadoNumeroPagina.getPagina().trim());

			if (fotoAsiento == null) {
				rutaImagenAsiento = "";
				return;
			}

			ByteArrayInputStream in = new ByteArrayInputStream(fotoAsiento);
			BufferedImage bImageFromConvert = ImageIO.read(in);

			String dtFile;

			dtFile = "//172.26.128.130/FotoSunarp/" + transaccion.toString() + "-" + idImg.toString() + "-" + tipo + "-"
					+ nroTotalPag + "-" + nroPagRef + "-" + pagina + ".jpg";
			ImageIO.write(bImageFromConvert, "jpg", new File(dtFile));

			// IP pública.
			setRutaImagenAsiento("http://190.116.36.140/FotoSunarp/" + transaccion.toString() + "-" + idImg.toString()
					+ "-" + tipo + "-" + nroTotalPag + "-" + nroPagRef + "-" + pagina + ".jpg");

			//

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}


	public void consultarAsientos() {
		
		
		if (tipoPersona == 3) {
			// La búsqeuda es por Número de partida
			

			try {

				if (numeroPartida == null || numeroPartida.equals("")) {
					WebMessages.messageError("Debe ingersar un Nümero de Partida.");
					return;
				}
				
				//buscamos datos de Oficina
				oficina = buscarOficinas.getCodZonaOficina(oficinaSelect);

				resultadoAsientos = new RespuestaPartidaBean();
				resultadoAsientos = consultaAsientosWS(oficina, numeroPartida, tipoRegistroSelect);

			} catch (Exception e) {
				// TODO: handle exception
			}

			return;
		}

		// Búsqueda desde Titularidad

		if (titularidad == null || titularidad.getOficina() == null) {
			WebMessages.messageError("No ha seleccionado una Propiedad.");
			return;
		}

		try {

			// BuscarCodigoSunarp buscarOficinas = new BuscarCodigoSunarp();
			// OficinaSunarpDTO oficinaDTO = new OficinaSunarpDTO();

			// Obtenemos el c´dogio de zona y oficina.
			oficina = buscarOficinas.getCodZonaOficina(titularidad.getOficina());

			// RespuestaPartidaBean asientos=new RespuestaPartidaBean();

			// Obtenemos los asientos
			resultadoAsientos = new RespuestaPartidaBean();

			resultadoAsientos = consultaAsientosWS(oficina, titularidad.getNumeroPartida(),
					buscarOficinas.getCodigoTipoRegistro(titularidad.getRegistro()).getCodigo());

		} finally {
			// TODO: handle finally clause
		}

	}

	private RespuestaPartidaBean consultaAsientosWS(OficinaSunarpDTO oficina, String nroPartida, String registro) {

		if (tipoPersona != 3 && titularidad == null) {
			WebMessages.messageError("No ha seleccionado una Propiedad.");
			return null;
		}

		// port1.listarAsientos(zona, oficina, partida, registro)
		/*
		 * Codigo 21000 - Registro de Propiedad Inmueble. Codigo 22000 - Registro de
		 * Personas Jurídicas. Codigo 23000 - Registro de Personas Naturales. Código
		 * 24000 - Registro de Bienes Muebles (Básicamente Propiedad Vehicular)
		 */

		// String registro;

		// BuscarCodigoSunarp buscar = new BuscarCodigoSunarp();

		// registro =
		// buscarOficinas.getCodigoTipoRegistro(titularidad.getRegistro()).getCodigo();

		if (registro == null) {
			WebMessages.messageError("No se ecnuentra el Tipo de REgistro: " + titularidad.getRegistro());
			return null;
		}

		if (oficina == null) {
			WebMessages.messageError("No ha seleccionado una Oficina.");
			return null;
		}

		RespuestaPartidaBean retorno = new RespuestaPartidaBean();
		
		/*
		WebMessages.messageError("Oficina "+oficina.getDescripcion());
		WebMessages.messageError("Partida "+nroPartida);
		WebMessages.messageError("REgistro "+registro);
		*/
		

		try {

			retorno = new RespuestaPartidaBean();

			PideService service1 = new PideService();
			PIDEWSService port1 = service1.getPIDEWSServicePort();

			retorno = port1.listarAsientos(oficina.getCodZona(), oficina.getCodOficina(), nroPartida, registro);

		} finally {
			// TODO: handle finally clause
		}

		return retorno;

	}
	
	public void registrovehicular ()
	{
		PideService service1 = new PideService();
		PIDEWSService port1 = service1.getPIDEWSServicePort();
		PropietarioBean propietarios=new PropietarioBean ();
		
		
		
		try {
			//buscamos datos de Oficina
			oficina = buscarOficinas.getCodZonaOficina(oficinaSelect);
			
			respuestaVehiculo=port1.verDetalleRPV(oficina.getCodZona(),oficina.getCodOficina(),placa);
			
			propietarios=respuestaVehiculo.getPropietarios();	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	public void selectMenu(Integer valor)
	{
		tipoPersona=valor;
	}
	

	public void setTipoPersona(Integer tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Integer getTipoPersona() {
		return tipoPersona;
	}

	public ArrayList<ResultadoTitularidad> getrsultadoTitularidad() {
		return rsultadoTitularidad;
	}

	public void setrsultadoTitularidad(ArrayList<ResultadoTitularidad> rsultadoTitularidad) {
		this.rsultadoTitularidad = rsultadoTitularidad;
	}

	public String getDniConsultaReniec() {
		return dniConsultaReniec;
	}

	public void setDniConsultaReniec(String dniConsultaReniec) {
		this.dniConsultaReniec = dniConsultaReniec;
	}

	public String getRutaFotoReniec() {
		return rutaFotoReniec;
	}

	public void setRutaFotoReniec(String rutaFotoReniec) {
		this.rutaFotoReniec = rutaFotoReniec;
	}

	public ResultadoConsulta getResultadoConsultaReniec() {
		return resultadoConsultaReniec;
	}

	public void setResultadoConsultaReniec(ResultadoConsulta resultadoConsultaReniec) {
		this.resultadoConsultaReniec = resultadoConsultaReniec;
	}

	public String getRUCConsultasSunat() {
		return RUCConsultasSunat;
	}

	public void setRUCConsultasSunat(String rUCConsultasSunat) {
		RUCConsultasSunat = rUCConsultasSunat;
	}

	public ResultadoTitularidad getTitularidad() {
		return titularidad;
	}

	public void setTitularidad(ResultadoTitularidad titularidad) {
		this.titularidad = titularidad;
	}

	public RespuestaPartidaBean getResultadoAsientos() {
		return resultadoAsientos;
	}

	public void setResultadoAsientos(RespuestaPartidaBean resultadoAsientos) {
		this.resultadoAsientos = resultadoAsientos;
	}

	public AsientosBean getResultadoAsiento() {
		return resultadoAsiento;
	}

	public void setResultadoAsiento(AsientosBean resultadoAsiento) {
		this.resultadoAsiento = resultadoAsiento;
	}

	public String getRutaImagenAsiento() {
		return rutaImagenAsiento;
	}

	public void setRutaImagenAsiento(String rutaImagenAsiento) {
		this.rutaImagenAsiento = rutaImagenAsiento;
	}

	public NumeroPaginasBean getResultadoNumeroPagina() {
		return resultadoNumeroPagina;
	}

	public void setResultadoNumeroPagina(NumeroPaginasBean resultadoNumeroPagina) {
		this.resultadoNumeroPagina = resultadoNumeroPagina;
	}

	public FichaBean getResuladoFicha() {
		return resuladoFicha;
	}

	public void setResuladoFicha(FichaBean resuladoFicha) {
		this.resuladoFicha = resuladoFicha;
	}

	public NumeroPaginasBean getResultadoNumeroPaginaFicha() {
		return resultadoNumeroPaginaFicha;
	}

	public void setResultadoNumeroPaginaFicha(NumeroPaginasBean resultadoNumeroPaginaFicha) {
		this.resultadoNumeroPaginaFicha = resultadoNumeroPaginaFicha;
	}

	public TomoFolioBean getResuladoFolio() {
		return resuladoFolio;
	}

	public void setResuladoFolio(TomoFolioBean resuladoFolio) {
		this.resuladoFolio = resuladoFolio;
	}

	public ArrayList<OficinaSunarpDTO> getOficinas() {
		return oficinas;
	}

	public void setOficinas(ArrayList<OficinaSunarpDTO> oficinas) {
		this.oficinas = oficinas;
	}

	public ArrayList<TipoRegistroSunarpDTO> gettiposRegistro() {
		return tiposRegistro;
	}

	public void settiposRegistro(ArrayList<TipoRegistroSunarpDTO> tiposRegistro) {
		this.tiposRegistro = tiposRegistro;
	}

	public OficinaSunarpDTO getOficina() {
		return oficina;
	}

	public void setOficina(OficinaSunarpDTO oficina) {
		this.oficina = oficina;
	}

	public String getTipoRegistro() {
		return tipoRegistroSelect;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistroSelect = tipoRegistro;
	}

	public String getNumeroPartida() {
		return numeroPartida;
	}

	public void setNumeroPartida(String numeroPartida) {
		this.numeroPartida = numeroPartida;
	}

	public String getOficinaSelect() {
		return oficinaSelect;
	}

	public void setOficinaSelect(String oficinaSelect) {
		this.oficinaSelect = oficinaSelect;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public RespuestaVehiculoBean getRespuestaVehiculo() {
		return respuestaVehiculo;
	}

	public void setRespuestaVehiculo(RespuestaVehiculoBean respuestaVehiculo) {
		this.respuestaVehiculo = respuestaVehiculo;
	}
	
	

	

}
