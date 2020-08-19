package com.sat.sisat.consumoWS;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;

import com.sat.sisat.administracion.business.AdministracionBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisatc.seguridad.dto.ConsultaReniecDTO;

import pe.gob.reniec.ws.PeticionConsulta;
import pe.gob.reniec.ws.ReniecConsultaDni;
import pe.gob.reniec.ws.ReniecConsultaDniPortType;
import pe.gob.reniec.ws.ResultadoConsulta;

@ManagedBean
@ViewScoped

public class ConsultaReniecManaged extends BaseManaged {

	@EJB
	AdministracionBoRemote administracionBo;

	private Boolean registrarAuditoria = Boolean.FALSE;
	private String DniConsulta = "";
	private String rutaImagen;
	private ResultadoConsulta resultadoConsulta = new ResultadoConsulta();

	public void consultaWSDirecto() throws Exception {
		// Solo desde aca se guardan los datos de auditoria.
		registrarAuditoria = Boolean.TRUE;
		consultaWS();
	}

	public ResultadoConsulta consultaWS() throws Exception {

		resultadoConsulta = new ResultadoConsulta();

		// Validamos el formato del DNI.
		if (DniConsulta.length() != 8 || DniConsulta == null || DniConsulta.isEmpty()) {
			WebMessages.messageError("El número de DNI. No cumple con el formato requerido.");
			return resultadoConsulta;
		}

		resultadoConsulta = new ResultadoConsulta();
		String rucUsuario = "20453807267";
		String dniUsuario, clave;

		// String rucUsuario = "20453807267",dniUsuario = "42555584", clave =
		// "42555584";

		// Extraemos las credenciales de consulta.
		dniUsuario = getSessionManaged().getUsuarioLogIn().getDniUsuario();
		clave = getSessionManaged().getUsuarioLogIn().getClaveREniec();

		try {

			PeticionConsulta peticionConsulta = new PeticionConsulta();

			peticionConsulta.setNuDniConsulta(DniConsulta);
			peticionConsulta.setNuDniUsuario(dniUsuario);
			peticionConsulta.setNuRucUsuario(rucUsuario);
			peticionConsulta.setPassword(clave);

			// Consultar WebServices.
			resultadoConsulta = consultar(peticionConsulta);

		} finally {
			// TODO: handle finally clause
		}

		if (resultadoConsulta.getCoResultado() == null)
			return resultadoConsulta;

		resultadoConsulta.getCoResultado().replaceAll("/", "");
		resultadoConsulta.getCoResultado().replaceAll("r", "");
		resultadoConsulta.getCoResultado().replaceAll("n", "");

		// 0001 El número de DNI corresponde a un menor de edad.
		switch (resultadoConsulta.getCoResultado().trim()) {
		case "0001":
			WebMessages.messageError("El número de DNI corresponde a un menor de edad.");
			return resultadoConsulta;
		case "0999":
			WebMessages.messageError("No se ha encontrado información para el número de DNI.");
			return resultadoConsulta;
		case "1000":
			WebMessages.messageError("Uno o más datos de la petición no son válidos.");
			return resultadoConsulta;
		case "1001":
			WebMessages.messageError("El DNI, RUC y contraseña no corresponden a un usuario válido.");
			return resultadoConsulta;
		case "1002":
			WebMessages.messageError("La contraseña para el DNI y RUC está caducada.");
			return resultadoConsulta;
		case "1003":
			WebMessages.messageError("Se ha alcanzado el límite de consultas permitidas por día.");
			return resultadoConsulta;
		case "1999":
			WebMessages.messageError("Error desconocido/inesperado.");
			return resultadoConsulta;
		}

		convertirFoto();

		// registramos datos de auditoria.
		if (registrarAuditoria) {

			ConsultaReniecDTO datos = new ConsultaReniecDTO();

			datos.setDniConsulta(dniUsuario);
			datos.setDniConsultado(DniConsulta);
			datos.setUsuarioID(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			datos.setTerminal(getSessionManaged().getTerminalLogIn());

			administracionBo.registrarConsultaReniec(datos);
			registrarAuditoria=Boolean.FALSE;
		}
		// Fin de registrar datos.

		return resultadoConsulta;

	}

	private static ResultadoConsulta consultar(PeticionConsulta arg0) {
		ReniecConsultaDni service = new ReniecConsultaDni();
		ReniecConsultaDniPortType port = service.getReniecConsultaDniHttpsSoap11Endpoint();
		return port.consultar(arg0);
	}

	private void convertirFoto() {

		if (resultadoConsulta.getDatosPersona().getFoto() == null) {
			setRutaImagen("");
			return;
		}

		try {
			// convert byte array back to BufferedImage
			ByteArrayInputStream in = new ByteArrayInputStream(resultadoConsulta.getDatosPersona().getFoto());
			BufferedImage bImageFromConvert = ImageIO.read(in);

			String dtFile;
			// String path = ctx.getRealPath("/");
			// dtFile = path + "tmp" + File.separator + DniConsulta + ".jpg";

			dtFile = "//172.26.128.130/FotoDNI/" + DniConsulta + ".jpg";
			ImageIO.write(bImageFromConvert, "jpg", new File(dtFile));

			setRutaImagen("http://190.116.36.140/FotoDNI/" + DniConsulta + ".jpg");

			//

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public String getDniConsulta() {
		return DniConsulta;
	}

	public void setDniConsulta(String dniConsulta) {
		DniConsulta = dniConsulta;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public ResultadoConsulta getResultadoConsulta() {
		return resultadoConsulta;
	}

	public void setResultadoConsulta(ResultadoConsulta resultadoConsulta) {
		this.resultadoConsulta = resultadoConsulta;
	}

}
