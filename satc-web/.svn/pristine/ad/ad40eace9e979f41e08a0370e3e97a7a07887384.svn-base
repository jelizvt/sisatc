package com.sat.sisat.predial.managed;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.cuponera.business.CuponeraBoRemote;
import com.sat.sisat.persistence.CrudServiceBean;

@ManagedBean
@ViewScoped
public class GenerarCuponeraManaged extends BaseManaged {

	private static final long serialVersionUID = -5181715322102028808L;
	private Integer personaId;
	@EJB
	CuponeraBoRemote cuponeraRemote;

	@PostConstruct
	public void init() {

	}

	public void previewDJ() {

	}

	public void guardar() {
		try {
			if (personaId != null) {
				cuponeraRemote.registraPersonaCuponera(personaId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void imprimirPdfPuCuponera() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			// parameters.put("fecI", new Timestamp(fechaInicio.getTime()));
			// parameters.put("fecF", new
			// Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));

			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporteCuponera() + "imprimir_PU_cuponera.jasper"),
							parameters, connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=imprimir_PU_cuponera.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
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

	public void imprimirPdfPrCuponera() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			// parameters.put("fecI", new Timestamp(fechaInicio.getTime()));
			// parameters.put("fecF", new
			// Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));

			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporteCuponera() + "imprimir_PR_cuponera.jasper"),
							parameters, connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=imprimir_PR_cuponera.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
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

	public void imprimirPdfNotificacionCuponera() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			// parameters.put("fecI", new Timestamp(fechaInicio.getTime()));
			// parameters.put("fecF", new
			// Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));

			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporteCuponera() + "imprimir_noti_cuponera.jasper"),
							parameters, connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=imprimir_notificacion_cuponera.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
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

	public void imprimirPdfEcCuponera() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			// parameters.put("fecI", new Timestamp(fechaInicio.getTime()));
			// parameters.put("fecF", new
			// Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));

			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporteCuponera() + "imprimir_ec_cuponera.jasper"),
							parameters, connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=imprimir_ec_cuponera.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
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

	public void imprimirPdfArbitrosCuponera() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			// parameters.put("fecI", new Timestamp(fechaInicio.getTime()));
			// parameters.put("fecF", new
			// Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));

			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporteCuponera() + "imprimir_arbitrios_cuponera.jasper"),
							parameters, connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=imprimir_arbitrios_cuponera.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
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

	public void imprimirPdfHr1Cuponera() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			// parameters.put("fecI", new Timestamp(fechaInicio.getTime()));
			// parameters.put("fecF", new
			// Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));

			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporteCuponera() + "imprimir_hr1_cuponera.jasper"),
							parameters, connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=imprimir_hr1_cuponera.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
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

	public void imprimirPdfHr2Cuponera() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			// parameters.put("fecI", new Timestamp(fechaInicio.getTime()));
			// parameters.put("fecF", new
			// Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));

			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporteCuponera() + "imprimir_hr2_cuponera.jasper"),
							parameters, connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=imprimir_hr2_cuponera.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
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

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
}
