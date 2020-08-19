package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
//import java.util.GregorianCalendar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcCuotasAgrupadasLote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcFirmante;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteActoPK;
import com.sat.sisat.persistence.entity.CcLoteConcepto;
import com.sat.sisat.persistence.entity.CcLoteConceptoPK;
import com.sat.sisat.persistence.entity.CcLoteCuota;
import com.sat.sisat.persistence.entity.CcLoteCuotaPK;
import com.sat.sisat.persistence.entity.CcLoteFirma;
import com.sat.sisat.persistence.entity.CcLoteFirmaPK;
import com.sat.sisat.persistence.entity.CcLoteTipoPersona;
import com.sat.sisat.persistence.entity.CcLoteTipoPersonaPK;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.GnConcepto;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnSubconcepto;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persistence.entity.PaDireccion;
//import com.sat.sisat.persistence.entity.PaPapeleta;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped


public class RegistroLoteOrdenPagoManaged extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;

	private Boolean isAccionRealizada = Boolean.FALSE;
	private Boolean isRecordatorioDeudas = Boolean.FALSE;
	
	private Boolean porSector=Boolean.FALSE;

	
	private Integer personaId = null;
	private Integer conceptoId;
	private Integer periodo;
	private BigDecimal montoMin;
	private BigDecimal montoMax;
	
	private ArrayList<GnSector> sectores =new ArrayList<GnSector>();
	private Integer sectorID=0;


	private HtmlComboBox cmbgnConcepto;
	private List<SelectItem> lstgnConcepto = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapgnConcepto = new HashMap<String, Integer>();
	private String cmbValueConcepto;

	private HtmlComboBox cmbCcTipoAgrupamiento;
	private List<SelectItem> lstCcTipoAgrupamiento = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapCcTipoAgrupamiento = new HashMap<String, Integer>();
	private String cmbValueCcTipoAgrupamientoBien;

	private List<GnSubconcepto> lstGnSubConcepto = null;
	private String[] lstGnSubConceptoSeleccionados;
	private HashMap<String, Integer> mapSubConceptoSeleccionados = new HashMap<String, Integer>();

	// agrupamiento tipo de persona
	private List<MpTipoPersona> lstTipoPersona = null;
	private String[] lstMpTipoPersonaSeleccionados = null;
	private HashMap<String, Integer> mapMpTipoPersonaSeleccionados = new HashMap<String, Integer>();

	private List<CcFirmante> lstCcFirmantes = null;
	private String[] lstCcFirmantesValue;
	private String[] lstCcFirmantesSeleccionado;
	private HashMap<String, Integer> mapFirmantesValue = new HashMap<String, Integer>();
	private List<FindCcCuotasAgrupadasLote> lstCuotasAgrupadasAnio = null;

	private CcLote ccLote = new CcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private FindCcLote findCcLote = new FindCcLote();
	private FindCcLote findCcLoteItem = new FindCcLote();
	private Integer lote_id;	
	
	private HtmlComboBox cmbHtmlOpUbicable;
	
	

	private String seleccionOp;
	// private List<CcTipoAgrupamiento> lstTipoAgrupamiento=null;
	private List<DtFechaVencimiento> lstCuotasAnio = null;
	private String[] lstMpCuotasAnioSeleccionados = null;
	private HashMap<String, Integer> mapMpCuotasAnioSeleccionados = new HashMap<String, Integer>();
	private Boolean soloPrimeraInscripcion;
	private List<FindCcLoteDetalleActo> lstlotePreliminar = null;
	private Boolean opParaUnaPersona = true;

	@PostConstruct
	public void init() {
		try {
			FindCcLote findCcLoteItem = (FindCcLote) getSessionMap().get("findCcLoteItems");
			if (findCcLoteItem != null) {
				setFindCcLoteItem(findCcLoteItem);
				setLote_id(findCcLoteItem.getLoteId());
				setConceptoId(findCcLoteItem.getConceptoId());
				setMontoMin(findCcLoteItem.getMontoInicio());
				setMontoMax(findCcLoteItem.getMontoFin());
				if (findCcLoteItem.getFlagUbicables().equals("Ubic.")) {
					this.setSeleccionOp("OPs Ubicables");
					// cmbHtmlOpUbicable.setValue("OPs Ubicables");
				}
				if (findCcLoteItem.getFlagUbicables().equals("Otros")) {
					setSeleccionOp("OPs Inubicables OTROS");
					// cmbHtmlOpUbicable.setValue("OPs Inubicables OTROS");
				}
				if (findCcLoteItem.getFlagUbicables().equals("S/N")) {
					setSeleccionOp("OPs Inubicables S/N");
					// cmbHtmlOpUbicable.setValue("OPs Inubicables S/N");
				}
			}
			lstGnSubConceptoSeleccionados = new String[] {};
			lstMpTipoPersonaSeleccionados = new String[] {};
			// lstMpTipoPersona
			lstTipoPersona = personaBo.getAllMpTipoPersona();
			
			sectores=controlycobranzaBo.getAllSector();
			
			
			
			Iterator<MpTipoPersona> it2 = lstTipoPersona.iterator();
			String temp2 = "";
			while (it2.hasNext()) {
				MpTipoPersona obj = it2.next();
				mapMpTipoPersonaSeleccionados.put(obj.getDescripcion().trim(), obj.getTipoPersonaId());
				temp2 = temp2 + obj.getDescripcion() + ",";
			}
			lstMpTipoPersonaSeleccionados = temp2.split(",");

			// CcTipoActo
			ccLote.setTipoLoteId(Constante.TIPO_ACTO_ORDEN_PAGO);
			cmbValueConcepto = "";
			cmbgnConcepto = null;
			lstGnSubConceptoSeleccionados = new String[0];
			lstGnSubConcepto = new ArrayList<GnSubconcepto>();
			viewcmbconcepto();
			loadFirmantes();
			loadCuotasAnio();
			// seleccionOp="OPs Ubicables";
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}

	public void loadCuotasAnio() throws Exception {
		if (conceptoId == null) {
			setConceptoId(1);
		}
		setPeriodo(periodo);
		if (periodo == null) {
			Calendar c = Calendar.getInstance();
			setPeriodo(c.get(Calendar.YEAR));
			// setConceptoId(1);
		}
		if (findCcLoteItem.getLoteId() != null) {
			lstCuotasAgrupadasAnio = controlycobranzaBo.getAllFindCuotasAgrupadasxLote(findCcLoteItem.getLoteId());
			setPeriodo(lstCuotasAgrupadasAnio.get(0).getAnioDeuda());
			String cuotasRecuperadas = lstCuotasAgrupadasAnio.get(0).getCuotas();
			lstMpCuotasAnioSeleccionados = cuotasRecuperadas.split(",");
			lstCuotasAnio = controlycobranzaBo.getAllMpCuotasxLoteIngresadas(findCcLoteItem.getLoteId());
		} else {
			lstCuotasAnio = controlycobranzaBo.getAllMpCuotasAnio(conceptoId, periodo);

			// lstCuotasAnio = controlycobranzaBo.getAllMpCuotasAnio(1, 2013);
			Iterator<DtFechaVencimiento> it2 = lstCuotasAnio.iterator();
			String temp2 = "";
			while (it2.hasNext()) {
				DtFechaVencimiento obj = it2.next();
				mapMpCuotasAnioSeleccionados.put("" + obj.getCuota(), obj.getCuota());
				temp2 = temp2 + "" + obj.getCuota() + ",";
			}
			lstMpCuotasAnioSeleccionados = temp2.split(",");
		}
	}

	public void exportarTablaExcel() {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("Cod Persona");
		row1.createCell(1).setCellValue("Apellidos y Nombres/ Razon Social");
		row1.createCell(2).setCellValue("Direccion");
		row1.createCell(3).setCellValue("Concepto");
		row1.createCell(4).setCellValue("Año Deuda");
		row1.createCell(5).setCellValue("Nro. Cuota");
		row1.createCell(6).setCellValue("Total Deuda.");
		row1.createCell(7).setCellValue("Nro. Valor.");
		row1.createCell(8).setCellValue("Sector");

		for (FindCcLoteDetalleActo data : lstlotePreliminar) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getDescripcionPersona());
			row.createCell(columnIndex++).setCellValue(data.getDireccion());
			row.createCell(columnIndex++).setCellValue(data.getDescripcionConcepto());
			row.createCell(columnIndex++).setCellValue(data.getAnioCuota());
			row.createCell(columnIndex++).setCellValue(data.getNroCuota());
			row.createCell(columnIndex++).setCellValue(data.getDeudaTotal().toString());
			row.createCell(columnIndex++).setCellValue(data.getNroActo());
			
			row.createCell(columnIndex++).setCellValue(data.getSector());
			
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition", "attachment;filename=exportando_a_excel.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}

	public void loadFirmantes() throws Exception {
		lstCcFirmantes = controlycobranzaBo.findCcFirmantesREC(Constante.TIPO_ACTO_ORDEN_PAGO);
		lstCcFirmantesValue = new String[lstCcFirmantes.size() - Constante.NUMERO_FIRMAS_OP];
		lstCcFirmantesSeleccionado = new String[Constante.NUMERO_FIRMAS_OP];
		int k = 0;
		for (int i = 0; i < lstCcFirmantes.size(); i++) {
			if (i < Constante.NUMERO_FIRMAS_OP)
				lstCcFirmantesSeleccionado[i] = lstCcFirmantes.get(i).getApellidosNombres() + "-"
						+ lstCcFirmantes.get(i).getCargo();
			else {
				lstCcFirmantesValue[k] = lstCcFirmantes.get(i).getApellidosNombres() + "-"
						+ lstCcFirmantes.get(i).getCargo();
				k++;
			}
			mapFirmantesValue.put(lstCcFirmantes.get(i).getApellidosNombres() + "-" + lstCcFirmantes.get(i).getCargo(),
					lstCcFirmantes.get(i).getFirmanteId());
		}

	}

	public void loadtipoLoteById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
			}
			cmbValueConcepto = "";
			cmbgnConcepto = null;
			lstGnSubConceptoSeleccionados = new String[0];
			lstGnSubConcepto = new ArrayList<GnSubconcepto>();

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadGnConceptoById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				conceptoId = (Integer) mapgnConcepto.get(value);
				setCmbValueConcepto(value);
			}
			viewsubconcepto();
			loadCuotasAnio();
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void viewsubconcepto() {
		try {
			mapSubConceptoSeleccionados.clear();
			lstGnSubConcepto = controlycobranzaBo.getAllGnSubConcepto(conceptoId);
			Iterator<GnSubconcepto> it2 = lstGnSubConcepto.iterator();
			StringBuffer temp2 = new StringBuffer();
			while (it2.hasNext()) {
				GnSubconcepto obj = it2.next();
				mapSubConceptoSeleccionados.put(obj.getDescripcion().trim(), obj.getId().getSubconceptoId());
				temp2.append(obj.getId().getSubconceptoId());
				temp2.append(",");
			}
			lstGnSubConceptoSeleccionados = temp2.toString().split(",");

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void viewcmbconcepto() {
		try {
			// CcTipoActo
			List<GnConcepto> lstCcTipoActo = controlycobranzaBo.getAllGnConcepto(Constante.TIPO_ACTO_ORDEN_PAGO);
			Iterator<GnConcepto> it1 = lstCcTipoActo.iterator();
			lstgnConcepto = new ArrayList<SelectItem>();

			while (it1.hasNext()) {
				GnConcepto obj = it1.next();
				lstgnConcepto.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getConceptoId())));
				mapgnConcepto.put(obj.getDescripcion().trim(), obj.getConceptoId());
			}
			if (findCcLoteItem != null) {
				setCmbValueConcepto(findCcLoteItem.getConcepto());
				viewsubconcepto();
			} else {
				cmbValueConcepto = "";
				cmbgnConcepto = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public boolean validarIngresoLote() {
		try {
			// FindMpPersona findMpPersonaTemp=new FindMpPersona();
			/**
			 * BigDecimal temp = BigDecimal.ZERO; if (montoMin == null ||
			 * montoMin.compareTo(temp) <= 0) {
			 * addErrorMessage(getMsg("cc.errormontoinicio")); return false; } if (montoMax
			 * == null || montoMax.compareTo(temp) <= 0) {
			 * addErrorMessage(getMsg("cc.errormontofin")); return false; }
			 */
			if (mapMpTipoPersonaSeleccionados == null) {
				addErrorMessage("Seleccione al menos un Tipo de Persona");
			}
			if (mapSubConceptoSeleccionados == null) {
				addErrorMessage(getMsg("cc.errorselectsubconceptos"));
			}
			if (mapMpCuotasAnioSeleccionados == null) {
				addErrorMessage(getMsg("cc.errorselectcuotas"));
			}
			if (personaId == null) {
				setPersonaId(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return true;
	}

	public void motrarLotePreliminar() {
		
		
		
		try {
			if (findCcLoteItem != null) {
				// loadCuotasAnio();
				if (validarIngresoLote()) {
					String cuotas = "";
					for (String s : lstMpCuotasAnioSeleccionados) {
						cuotas += s + ",";
					}
					if (findCcLoteItem.getLoteId() != null) {
						lstlotePreliminar = controlycobranzaBo.getAllFindCcLoteFinalOP(findCcLoteItem.getLoteId());
					} else {
						boolean cuotasNoVencidas = false;
						for (String nroCuota : lstMpCuotasAnioSeleccionados) {
							// cuota vencida
							if (DateUtil.getCurrentDate().before(lstCuotasAnio
									.get(Integer.parseInt(String.valueOf(nroCuota)) - 1).getFechaVencimiento())) {
								cuotasNoVencidas = true;
								break;
							} else {
								cuotasNoVencidas = false;
							}
						}
						if (cuotasNoVencidas == false) {
							if (getSeleccionOp().equals("OPs Ubicables") && conceptoId == 2) {// vehicular ubicables
								lstlotePreliminar = controlycobranzaBo.getAllFindCcLotePreliminarOpVehicular(conceptoId,
										periodo, cuotas, montoMin, montoMax, personaId);
								if (lstlotePreliminar == null || lstlotePreliminar.size() == 0) {
									addErrorMessage(getMsg(
											"No hay OPs para Mostrar, ya fueron generadas en otro Lote. Verifique!!!"));
								} else {
									ccLote.setFlagUbicables("2");
								}
							} else if (getSeleccionOp().equals("OPs Ubicables") && conceptoId == 1) {// �predial
																										// ubicables
																										// anio
																										// seleccionado
								//lstlotePreliminar = controlycobranzaBo.getAllFindCcLotePreliminarOpPredial(conceptoId,periodo, cuotas, montoMin, montoMax, personaId);
								
								Integer sectorIDBuscar=sectorID;
								//El -1 indica q no se desea por sector.
								
								if (!porSector ) sectorIDBuscar=-1; 
								lstlotePreliminar = controlycobranzaBo.getAllFindCcLotePreliminarOpPredialGeneral	(conceptoId,periodo, cuotas, montoMin, montoMax, personaId,sectorIDBuscar,1,getSessionManaged().getUsuarioLogIn().getUsuarioId());										
																													
								
								
								
								
								if (lstlotePreliminar == null || lstlotePreliminar.size() == 0) {
									addErrorMessage(getMsg(
											"No hay OPs para Mostrar, ya fueron generadas en otro Lote. Verifique!!!"));
								} else {
									ccLote.setFlagUbicables("2");
								}
							}
							// else if
							// (getSeleccionOp().equals("OPs Ubicables")&&conceptoId==1)
							// {//�predial ubicables primera inscricion anio
							// seleccionado
							// lstlotePreliminar =
							// controlycobranzaBo.getAllFindCcLotePreliminarOpPredial(conceptoId,
							// periodo, cuotas,montoMin, montoMax);
							// if (lstlotePreliminar == null ||
							// lstlotePreliminar.size() == 0) {
							// addErrorMessage(getMsg("No hay OPs para Mostrar, ya fueron generadas en otro
							// Lote. Verifique!!!"));
							// } else {
							// ccLote.setFlagUbicables("2");
							// }
							// }
							else if (getSeleccionOp().equals("OPs Inubicables S/N") && conceptoId == 2) {// vehicular
																											// s/n anio
																											// seleccionado
								lstlotePreliminar = controlycobranzaBo.getAllFindCcLotePreliminarOpVehicularSN(
										conceptoId, periodo, cuotas, montoMin, montoMax, personaId);
								if (lstlotePreliminar == null || lstlotePreliminar.size() == 0) {
									addErrorMessage(getMsg(
											"No hay OPs para Mostrar, ya fueron generadas en otro Lote. Verifique!!!"));
								} else {
									ccLote.setFlagUbicables("0");
								}
							} else if (getSeleccionOp().equals("OPs Inubicables S/N") && conceptoId == 1) {// predial
																											// s/n anio
																											// seleccionado
								
								//lstlotePreliminar = controlycobranzaBo.getAllFindCcLotePreliminarOpPredialSn(conceptoId,periodo, cuotas, montoMin, montoMax, personaId);
								Integer sectorIDBuscar=sectorID;
								//El -1 indica q no se desea por sector.
								
								if (!porSector ) sectorIDBuscar=-1;
								
								lstlotePreliminar = controlycobranzaBo.getAllFindCcLotePreliminarOpPredialGeneral	(conceptoId,periodo, cuotas, montoMin, montoMax, personaId,sectorIDBuscar,2,getSessionManaged().getUsuarioLogIn().getUsuarioId());
								
								if (lstlotePreliminar == null || lstlotePreliminar.size() == 0) {
									addErrorMessage(getMsg(
											"No hay OPs para Mostrar, ya fueron generadas en otro Lote. Verifique!!!"));
								} else {
									ccLote.setFlagUbicables("0");
								}
							}
							// else if
							// (getSeleccionOp().equals("OPs Inubicables S/N")&&conceptoId==1)
							// {//predial s/n primera inscricion anio
							// seleccionado
							// lstlotePreliminar =
							// controlycobranzaBo.getAllFindCcLotePreliminarOpPredialSn(conceptoId,
							// periodo, cuotas,montoMin, montoMax);
							// if (lstlotePreliminar == null
							// || lstlotePreliminar.size() == 0) {
							// addErrorMessage(getMsg("No hay OPs para Mostrar, ya fueron generadas en otro
							// Lote. Verifique!!!"));
							// } else {
							// ccLote.setFlagUbicables("0");
							// }
							// }
							else if (getSeleccionOp().equals("OPs Inubicables OTROS") && conceptoId == 2) {// inubicables
																											// vehicular
																											// otros
																											// anio
																											// seleccionado
								lstlotePreliminar = controlycobranzaBo.getAllFindCcLotePreliminarOpVehicularOtros(
										conceptoId, periodo, cuotas, montoMin, montoMax, personaId);
								if (lstlotePreliminar == null || lstlotePreliminar.size() == 0) {
									addErrorMessage(getMsg(
											"No hay OPs para Mostrar, ya fueron generadas en otro Lote. Verifique!!!"));
								} else {
									ccLote.setFlagUbicables("1");
								}
							} else if (getSeleccionOp().equals("OPs Inubicables OTROS") && conceptoId == 1) {// inubicables
																												// predial
																												// otros
																												// anio
																												// seleccionado
								//lstlotePreliminar = controlycobranzaBo.getAllFindCcLotePreliminarOpPredialOtros(conceptoId, periodo, cuotas, montoMin, montoMax, personaId);
								Integer sectorIDBuscar=sectorID;
								//El -1 indica q no se desea por sector.
								
								if (!porSector ) sectorIDBuscar=-1;
								
								lstlotePreliminar = controlycobranzaBo.getAllFindCcLotePreliminarOpPredialGeneral	(conceptoId,periodo, cuotas, montoMin, montoMax, personaId,sectorIDBuscar,3,getSessionManaged().getUsuarioLogIn().getUsuarioId());
								
								if (lstlotePreliminar == null || lstlotePreliminar.size() == 0) {
									addErrorMessage(getMsg(
											"No hay OPs para Mostrar, ya fueron generadas en otro Lote. Verifique!!!"));
								} else {
									ccLote.setFlagUbicables("1");
								}
							}
							// else if (getSeleccionOp().equals(
							// "OPs Inubicables OTROS")&&conceptoId==1)
							// {//inubicables predial otros primera inscricion
							// anio
							// seleccionado
							// lstlotePreliminar =
							// controlycobranzaBo.getAllFindCcLotePreliminarOpPredialOtros(conceptoId,
							// periodo, cuotas,montoMin, montoMax);
							// if (lstlotePreliminar == null
							// || lstlotePreliminar.size() == 0) {
							// addErrorMessage(getMsg("No hay OPs para Mostrar, ya fueron generadas en otro
							// Lote. Verifique!!!"));
							// } else {
							// ccLote.setFlagUbicables("1");
							// }
							// }
						} else {
							addErrorMessage(getMsg(
									"Seleccione Correctamente las cuotas. Se ha seleccionado Cuotas No vencidas."));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadSeleccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			this.setSeleccionOp(value);

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	
	

	public void descargarPreviewOrdenesPagoExcel() {
		try {
			if (findCcLoteItem != null) {
				if (validarIngresoLote()) {
					String cuotas = "";
					for (String s : lstMpCuotasAnioSeleccionados) {
						cuotas += s + ",";
					}
					java.sql.Connection connection = null;
					try {
						String cadena = null;
						String cadena2 = null;

						if (getSeleccionOp().equals("OPs Ubicables") && conceptoId == 1) {
							cadena = "report_op_predial_ubicables.jasper";
							cadena2 = "PreliminarPredial";
						}
						if (getSeleccionOp().equals("OPs Ubicables") && conceptoId == 2) {
							cadena = "report_op_vehicular_ubicables.jasper";
							cadena2 = "PreliminarVehicular";
						}

						if (getSeleccionOp().equals("OPs Inubicables S/N") && conceptoId == 2) {
							cadena = "report_op_vehicular_sinnumero.jasper";
							cadena2 = "PreliminarVehicular";
						}
						if (getSeleccionOp().equals("OPs Inubicables S/N") && conceptoId == 1) {
							cadena = "report_op_predial_sinnumero.jasper";
							cadena2 = "PreliminarPredial";
						}
						if (getSeleccionOp().equals("OPs Inubicables OTROS") && conceptoId == 2) {
							cadena = "report_op_vehicular_otros.jasper";
							cadena2 = "PreliminarVehicular";
						}
						if (getSeleccionOp().equals("OPs Inubicables OTROS") && conceptoId == 1) {
							cadena = "report_op_predial_otros.jasper";
							cadena2 = "PreliminarPredial";
						}

						CrudServiceBean connj = CrudServiceBean.getInstance();
						connection = connj.connectJasper();
						HashMap<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("p_concepto", conceptoId);
						parameters.put("p_anio", periodo);
						parameters.put("p_cuotas", cuotas);
						parameters.put("p_monto_min", montoMin);
						parameters.put("p_monto_max", montoMax);
						parameters.put("p_persona_id", personaId);
						JasperPrint jasperPrint = JasperFillManager
								.fillReport((SATWEBParameterFactory.getPathReporte() + cadena), parameters, connection);

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
						exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
								cadena + getSeleccionOp() + ".xls");
						exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
						exporterXls.exportReport();

						HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
								.getExternalContext().getResponse();
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + cadena2 + getSeleccionOp() + ".xls");
						response.setContentLength(output.size());
						ServletOutputStream servletOutputStream = response.getOutputStream();
						servletOutputStream.write(output.toByteArray(), 0, output.size());
						servletOutputStream.flush();
						servletOutputStream.close();
						FacesContext.getCurrentInstance().responseComplete();
					} catch (Exception jre) {
						jre.printStackTrace();
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void guardar() {
		try {
			if (lstlotePreliminar.size() != 0 && lstlotePreliminar != null) {
				if (validarIngresoLote()) {
					lote_id = generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
					ccLote.setLoteId(lote_id);
					// ccLote.setTipoLote(Constante.TIPO_LOTE_ORDINARIO);
					ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);
					ccLote.setAnnoLote(getPeriodo());
					isAccionRealizada = Boolean.TRUE;
					ccLote.setFechaLote(DateUtil.getCurrentDate());
					ccLote.setEstado(Constante.ESTADO_ACTIVO);
					if (montoMin == null || montoMin.intValue() == 0 || montoMax == null || montoMax.intValue() == 0) {
						setMontoMin(BigDecimal.valueOf(0));
						setMontoMax(BigDecimal.valueOf(9999999));
						ccLote.setMontoInicio(BigDecimal.valueOf(0));
						ccLote.setMontoFin(BigDecimal.valueOf(9999999));
					} else {
						ccLote.setMontoInicio(montoMin);
						ccLote.setMontoFin(montoMax);
					}
					ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
					ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
					ccLote = controlycobranzaBo.create(ccLote);
					// INSERT CC_LOTE_ACTO
					Integer lote_acto_id = generalBo.ObtenerCorrelativoTabla("cc_lote_acto", 1);
					CcLoteActoPK id = new CcLoteActoPK();
					id.setLoteActoId(lote_acto_id);
					id.setLoteId(ccLote.getLoteId());
					ccLoteActo.setId(id);
					ccLoteActo.setTipoActoId(Constante.TIPO_ACTO_ORDEN_PAGO_ID);
					ccLoteActo.setEstado(Constante.ESTADO_ACTIVO);
					ccLoteActo = controlycobranzaBo.create(ccLoteActo);
					/**
					 * if(fechaVencimiento != null) ccLoteActo.setFechaVencimiento
					 * (DateUtil.dateToSqlTimestamp (fechaVencimiento));
					 */
					// INSERT CC_LOTE_CONCEPTO

					CcLoteConcepto ccLoteConcepto;
					Integer lote_concepto_id;
					if (lstGnSubConceptoSeleccionados != null && lstGnSubConceptoSeleccionados.length > 0) {
						for (String p : lstGnSubConceptoSeleccionados) {
							lote_concepto_id = generalBo.ObtenerCorrelativoTabla("cc_lote_concepto", 1);
							ccLoteConcepto = new CcLoteConcepto();
							ccLoteConcepto.setConceptoId(conceptoId);
							CcLoteConceptoPK id1 = new CcLoteConceptoPK();
							id1.setLoteActoId(ccLoteActo.getId().getLoteActoId());
							id1.setLoteId(ccLote.getLoteId());
							id1.setLoteConceptoId(lote_concepto_id);
							ccLoteConcepto.setId(id1);
							ccLoteConcepto.setSubconceptoId(Integer.parseInt(p));

							ccLoteConcepto.setEstado(Constante.ESTADO_ACTIVO);
							controlycobranzaBo.create(ccLoteConcepto);
						}
					}
					// INSERT CC_LOTE_TIPO_PERSONA
					if (lstMpTipoPersonaSeleccionados != null && lstMpTipoPersonaSeleccionados.length > 0) {
						for (String p1 : lstMpTipoPersonaSeleccionados) {
							Integer lote_tipo_persona_id = generalBo.ObtenerCorrelativoTabla("cc_lote_tipo_persona", 1);
							CcLoteTipoPersona ccLoteTipoPersona = new CcLoteTipoPersona();
							CcLoteTipoPersonaPK id4 = new CcLoteTipoPersonaPK();
							id4.setLoteId(ccLote.getLoteId());
							id4.setLoteTipoPersonaId(lote_tipo_persona_id);
							id4.setTipoPersonaId(mapMpTipoPersonaSeleccionados.get(p1));
							ccLoteTipoPersona.setId(id4);
							ccLoteTipoPersona.setEstado(Constante.ESTADO_ACTIVO);
							controlycobranzaBo.create(ccLoteTipoPersona);
						}
					}
					// INSERT CC_LOTE_CUOTAS
					if (lstMpCuotasAnioSeleccionados != null && lstMpCuotasAnioSeleccionados.length > 0) {
						for (String p1 : lstMpCuotasAnioSeleccionados) {
							Integer lote_cuota_id = generalBo.ObtenerCorrelativoTabla("cc_lote_cuota", 1);
							CcLoteCuota ccLoteCuota = new CcLoteCuota();
							CcLoteCuotaPK id4 = new CcLoteCuotaPK();
							id4.setLoteId(ccLote.getLoteId());
							id4.setLoteCuotaId(lote_cuota_id);
							id4.setLoteActoId(ccLoteActo.getId().getLoteActoId());
							ccLoteCuota.setId(id4);
							ccLoteCuota.setCuota(mapMpCuotasAnioSeleccionados.get(p1));
							ccLoteCuota.setAnnoCuota(periodo);
							ccLoteCuota.setEstado(Constante.ESTADO_ACTIVO);
							controlycobranzaBo.create(ccLoteCuota);
						}
					}
					// INSERT CC_LOTE_FIRMA
					if (lstCcFirmantesSeleccionado != null && lstCcFirmantesSeleccionado.length > 0) {
						for (String p1 : lstCcFirmantesSeleccionado) {
							Integer lote_firma_id = generalBo.ObtenerCorrelativoTabla("cc_lote_firma", 1);
							CcLoteFirma ccLoteFirma = new CcLoteFirma();
							CcLoteFirmaPK id5 = new CcLoteFirmaPK();
							id5.setLoteId(ccLote.getLoteId());
							id5.setLoteFirmaId(lote_firma_id);
							ccLoteFirma.setFirmanteId(mapFirmantesValue.get(p1));
							ccLoteFirma.setId(id5);
							ccLoteFirma.setEstado(Constante.ESTADO_ACTIVO);
							controlycobranzaBo.create(ccLoteFirma);
						}

					}
					findCcLoteItem = controlycobranzaBo.getFindCcLote(lote_id);
					getSessionMap().put("findCcLoteItem", findCcLoteItem);
				}
			} else {
				addErrorMessage(getMsg("Las cuotas seleccionadas, ya fueron generadas en otro Lote. Verifique!!!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}

	public void checkOpParaUnContribuyente(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		setPersonaId(null);
		if (check == Boolean.TRUE) {
			setOpParaUnaPersona(true);
		} else {
			setOpParaUnaPersona(false);
		}
	}
	
	
	public void porSector()
	{
		porSector=!porSector;
	}
	

	public void generacionValores() {
		try {
			if (findCcLoteItem != null && findCcLoteItem.getLoteId() != null) {
				if (lstlotePreliminar != null && lstlotePreliminar.size() != 0) {
					Integer usuarioId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
					String cuotas = "";
					for (String s : lstMpCuotasAnioSeleccionados) {
						cuotas += s + ",";
					}
					// controlycobranzaBo.registrarActoOP(findCcLoteItem.getLoteId(),
					// periodo, conceptoId, cuotas, usuarioId);
					if (getSeleccionOp().equals("OPs Ubicables") && conceptoId == 2) {// solo ubicables
						controlycobranzaBo.registrarActoOP(findCcLoteItem.getLoteId(), periodo, conceptoId, cuotas,
								usuarioId, personaId);
					}

					if (getSeleccionOp().equals("OPs Ubicables") && conceptoId == 1) {// solo ubicables de predial
						//controlycobranzaBo.registrarActoOpUbicablesPredial(findCcLoteItem.getLoteId(), periodo,conceptoId, cuotas, usuarioId, personaId);
						controlycobranzaBo.registrarActoOpPredialGeneral(findCcLoteItem.getLoteId(), usuarioId, getSessionManaged().getUsuarioLogIn().getTerminal());
					}

					if (getSeleccionOp().equals("OPs Inubicables S/N") && conceptoId == 2) {// inublicables S/N
						controlycobranzaBo.registrarActoOPInubicalbesSN(findCcLoteItem.getLoteId(), periodo, conceptoId,
								cuotas, usuarioId, personaId);
					}

					if (getSeleccionOp().equals("OPs Inubicables S/N") && conceptoId == 1) {// inublicables S/N
						//controlycobranzaBo.registrarActoOpSnPredial(findCcLoteItem.getLoteId(), periodo, conceptoId,cuotas, usuarioId, personaId);
						controlycobranzaBo.registrarActoOpPredialGeneral(findCcLoteItem.getLoteId(), usuarioId, getSessionManaged().getUsuarioLogIn().getTerminal());
					}

					if (getSeleccionOp().equals("OPs Inubicables OTROS") && conceptoId == 2) {// Otros
						controlycobranzaBo.registrarActoOPInubicablesOtros(findCcLoteItem.getLoteId(), periodo,
								conceptoId, cuotas, usuarioId, personaId);
					}

					if (getSeleccionOp().equals("OPs Inubicables OTROS") && conceptoId == 1) {// Otros
						//controlycobranzaBo.registrarActoOpOtrosPredial(findCcLoteItem.getLoteId(), periodo, conceptoId,cuotas, usuarioId, personaId);
						controlycobranzaBo.registrarActoOpPredialGeneral(findCcLoteItem.getLoteId(), usuarioId, getSessionManaged().getUsuarioLogIn().getTerminal());
					}

					ccLote = controlycobranzaBo.findCcLote(getFindCcLoteItem().getLoteId());
					lote_id = ccLote.getLoteId();
					lstlotePreliminar = controlycobranzaBo.getAllFindCcLoteFinalOP(findCcLoteItem.getLoteId());
					findCcLoteItem = controlycobranzaBo.getFindCcLote(findCcLoteItem.getLoteId());
					System.out.println("resultado de generar valores OP " + lstlotePreliminar.size());
				} else {
					addErrorMessage(getMsg("No hay OPs por Generar."));
				}
			} else {
				addErrorMessage(getMsg("Primero Guarde el Lote a Generar."));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void mostrarValores() throws Exception {
		// try{
		if (findCcLoteItem != null && findCcLoteItem.getLoteId() != null) {
			lstlotePreliminar = controlycobranzaBo.getAllFindCcLoteFinalOP(findCcLoteItem.getLoteId());
			if (lstlotePreliminar != null && lstlotePreliminar.size() != 0) {
				findCcLoteItem.setFlag_generacion("2");
			} else {
				addErrorMessage(getMsg("No hay OP por mostrar, verifique!!! ya fueron generadas en otro Lote."));
			}
		} else {
			addErrorMessage(getMsg("No hay OP por mostrar, verifique!!!."));
		}
	}

	public void generacionArchivos() {
		if (conceptoId == 1) {
			generacionArchivos1();
		} else if (conceptoId == 2) {
			generacionArchivos2();
		}
	}

	public void generacionOPsVehicularSinNotificarySinPagarExcel() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					// String path_context =
					// FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					// String path_report = path_context + "/sisat/reportes/";
					// String path_imagen = path_context +
					// "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("anio", Integer.parseInt(String.valueOf(findCcLoteItem.getAnnoLote())));
					// parameters.put("ruta_image",path_imagen);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "reporte_ops_vehicular_inubicables.jasper"),
							parameters, connection);

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
					exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
							"Ops_inubicables_vehicular" + ".xls");
					exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
					exporterXls.exportReport();

					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + "Ops_inubicables_vehicular" + ".xls");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0, output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();

				} catch (Exception jre) {
					jre.printStackTrace();
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
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generacionOpsVehicularSinPagarExcel() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					// String path_context =
					// FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					// String path_report = path_context + "/sisat/reportes/";
					// String path_imagen = path_context +
					// "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("anio", Integer.parseInt(String.valueOf(findCcLoteItem.getAnnoLote())));
					// parameters.put("ruta_image",path_imagen);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "reporte_ops_vehicular_sinpagar.jasper"),
							parameters, connection);

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
					exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
							"Ops_inubicables_vehicular" + ".xls");
					exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
					exporterXls.exportReport();

					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + "Ops_sinpagar_vehicular" + ".xls");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0, output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();

				} catch (Exception jre) {
					jre.printStackTrace();
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
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generacionInubicablesPredialExcel() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					// String path_context =
					// FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					// String path_report = path_context + "/sisat/reportes/";
					// String path_imagen = path_context +
					// "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("anio", Integer.parseInt(String.valueOf(findCcLoteItem.getAnnoLote())));
					// parameters.put("ruta_image",path_imagen);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "reporte_ops_predial_inubicables.jasper"),
							parameters, connection);

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
					exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Ops_inubicables_predial" + ".xls");
					exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
					exporterXls.exportReport();

					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + "Ops_inubicables_predial" + ".xls");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0, output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();

				} catch (Exception jre) {
					jre.printStackTrace();
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
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generacionArchivos1() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				// java.sql.Connection connec = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					// CrudServiceBean conxi = CrudServiceBean.getInstance();
					// connec = conxi.connectImage();
					String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context + "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					// parameters.put("SUBREPORT_CONNECTION", connec);
					parameters.put("p_lote_id", findCcLoteItem.getLoteId());
					parameters.put("ruta_imagen", path_imagen);
					// parameters.put("p_periodo",
					// findCcLoteItem.getAnnoLote());
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "op_orden_pago_predial.jasper"), parameters,
							connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, output);
					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition", "attachment;filename=Ordenes_pago_predial.pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0, output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
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
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generacionArchivos2() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				// java.sql.Connection connec = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					// CrudServiceBean conxi = CrudServiceBean.getInstance();
					// connec = conxi.connectImage();
					String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context + "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					// parameters.put("SUBREPORT_CONNECTION", connec);
					parameters.put("p_lote_id", findCcLoteItem.getLoteId());
					parameters.put("ruta_imagen", path_imagen);
					// parameters.put("p_periodo",
					// findCcLoteItem.getAnnoLote());
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "op_orden_pago_vehicular.jasper"), parameters,
							connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, output);
					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition", "attachment;filename=Ordenes_pago_vehicular.pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0, output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
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
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public String sendRedirectPrincipal() {
		return "/sisat/principal.xhtml?faces-redirect=true";
	}

	public String salir() {
		getSessionMap().remove("findCcLoteItems");
		return sendRedirectPrincipal();
	}

	public void sendRedirectPrincipalListener() {
		try {
			getExternalContext().redirect("principal.jsf");
		} catch (IOException ex) {
			// TODO : Controller exception
			System.out.println("Pagina no encontrada");
		}
	}

	public void exportarConsolidadoTablaExcel() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("p_lote_id", findCcLoteItem.getLoteId());

					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "reporte_op_lista_acumulada.jasper"), parameters,
							connection);
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
					exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
							"Reporte Ops Lote " + findCcLoteItem.getLoteId() + ".xls");
					exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
					exporterXls.exportReport();

					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + "Reporte Ops Lote " + findCcLoteItem.getLoteId() + ".xls");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0, output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();

				} catch (Exception jre) {
					jre.printStackTrace();
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
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public HtmlComboBox getCmbgnConcepto() {
		return cmbgnConcepto;
	}

	public void setCmbgnConcepto(HtmlComboBox cmbgnConcepto) {
		this.cmbgnConcepto = cmbgnConcepto;
	}

	public List<SelectItem> getLstgnConcepto() {
		return lstgnConcepto;
	}

	public void setLstgnConcepto(List<SelectItem> lstgnConcepto) {
		this.lstgnConcepto = lstgnConcepto;
	}

	public HashMap<String, Integer> getMapgnConcepto() {
		return mapgnConcepto;
	}

	public void setMapgnConcepto(HashMap<String, Integer> mapgnConcepto) {
		this.mapgnConcepto = mapgnConcepto;
	}

	public String getCmbValueConcepto() {
		return cmbValueConcepto;
	}

	public void setCmbValueConcepto(String cmbValueConcepto) {
		this.cmbValueConcepto = cmbValueConcepto;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public List<GnSubconcepto> getLstGnSubConcepto() {
		return lstGnSubConcepto;
	}

	public void setLstGnSubConcepto(List<GnSubconcepto> lstGnSubConcepto) {
		this.lstGnSubConcepto = lstGnSubConcepto;
	}

	public Boolean getIsAccionRealizada() {
		return isAccionRealizada;
	}

	public void setIsAccionRealizada(Boolean isAccionRealizada) {
		this.isAccionRealizada = isAccionRealizada;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public CcLote getCcLote() {
		return ccLote;
	}

	public void setCcLote(CcLote ccLote) {
		this.ccLote = ccLote;
	}

	public CcLoteActo getCcLoteActo() {
		return ccLoteActo;
	}

	public void setCcLoteActo(CcLoteActo ccLoteActo) {
		this.ccLoteActo = ccLoteActo;
	}

	public String[] getLstGnSubConceptoSeleccionados() {
		return lstGnSubConceptoSeleccionados;
	}

	public void setLstGnSubConceptoSeleccionados(String[] lstGnSubConceptoSeleccionados) {
		this.lstGnSubConceptoSeleccionados = lstGnSubConceptoSeleccionados;
	}

	public String[] getLstMpTipoPersonaSeleccionados() {
		return lstMpTipoPersonaSeleccionados;
	}

	public void setLstMpTipoPersonaSeleccionados(String[] lstMpTipoPersonaSeleccionados) {
		this.lstMpTipoPersonaSeleccionados = lstMpTipoPersonaSeleccionados;
	}

	public HashMap<String, Integer> getMapMpTipoPersonaSeleccionados() {
		return mapMpTipoPersonaSeleccionados;
	}

	public void setMapMpTipoPersonaSeleccionados(HashMap<String, Integer> mapMpTipoPersonaSeleccionados) {
		this.mapMpTipoPersonaSeleccionados = mapMpTipoPersonaSeleccionados;
	}

	public List<MpTipoPersona> getLstTipoPersona() {
		return lstTipoPersona;
	}

	public void setLstTipoPersona(List<MpTipoPersona> lstTipoPersona) {
		this.lstTipoPersona = lstTipoPersona;
	}

	public HashMap<String, Integer> getMapSubConceptoSeleccionados() {
		return mapSubConceptoSeleccionados;
	}

	public void setMapSubConceptoSeleccionados(HashMap<String, Integer> mapSubConceptoSeleccionados) {
		this.mapSubConceptoSeleccionados = mapSubConceptoSeleccionados;
	}

	public FindCcLote getFindCcLote() {
		return findCcLote;
	}

	public void setFindCcLote(FindCcLote findCcLote) {
		this.findCcLote = findCcLote;
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

	public HtmlComboBox getCmbCcTipoAgrupamiento() {
		return cmbCcTipoAgrupamiento;
	}

	public void setCmbCcTipoAgrupamiento(HtmlComboBox cmbCcTipoAgrupamiento) {
		this.cmbCcTipoAgrupamiento = cmbCcTipoAgrupamiento;
	}

	public List<SelectItem> getLstCcTipoAgrupamiento() {
		return lstCcTipoAgrupamiento;
	}

	public void setLstCcTipoAgrupamiento(List<SelectItem> lstCcTipoAgrupamiento) {
		this.lstCcTipoAgrupamiento = lstCcTipoAgrupamiento;
	}

	public HashMap<String, Integer> getMapCcTipoAgrupamiento() {
		return mapCcTipoAgrupamiento;
	}

	public void setMapCcTipoAgrupamiento(HashMap<String, Integer> mapCcTipoAgrupamiento) {
		this.mapCcTipoAgrupamiento = mapCcTipoAgrupamiento;
	}

	public String getCmbValueCcTipoAgrupamientoBien() {
		return cmbValueCcTipoAgrupamientoBien;
	}

	public void setCmbValueCcTipoAgrupamientoBien(String cmbValueCcTipoAgrupamientoBien) {
		this.cmbValueCcTipoAgrupamientoBien = cmbValueCcTipoAgrupamientoBien;
	}

	public Boolean getIsRecordatorioDeudas() {
		return isRecordatorioDeudas;
	}

	public void setIsRecordatorioDeudas(Boolean isRecordatorioDeudas) {
		this.isRecordatorioDeudas = isRecordatorioDeudas;
	}

	public List<CcFirmante> getLstCcFirmantes() {
		return lstCcFirmantes;
	}

	public void setLstCcFirmantes(List<CcFirmante> lstCcFirmantes) {
		this.lstCcFirmantes = lstCcFirmantes;
	}

	public String[] getLstCcFirmantesValue() {
		return lstCcFirmantesValue;
	}

	public void setLstCcFirmantesValue(String[] lstCcFirmantesValue) {
		this.lstCcFirmantesValue = lstCcFirmantesValue;
	}

	public String[] getLstCcFirmantesSeleccionado() {
		return lstCcFirmantesSeleccionado;
	}

	public void setLstCcFirmantesSeleccionado(String[] lstCcFirmantesSeleccionado) {
		this.lstCcFirmantesSeleccionado = lstCcFirmantesSeleccionado;
	}

	public HashMap<String, Integer> getMapFirmantesValue() {
		return mapFirmantesValue;
	}

	public void setMapFirmantesValue(HashMap<String, Integer> mapFirmantesValue) {
		this.mapFirmantesValue = mapFirmantesValue;
	}

	public List<DtFechaVencimiento> getLstCuotasAnio() {
		return lstCuotasAnio;
	}

	public void setLstCuotasAnio(List<DtFechaVencimiento> lstCuotasAnio) {
		this.lstCuotasAnio = lstCuotasAnio;
	}

	public String[] getLstMpCuotasAnioSeleccionados() {
		return lstMpCuotasAnioSeleccionados;
	}

	public void setLstMpCuotasAnioSeleccionados(String[] lstMpCuotasAnioSeleccionados) {
		this.lstMpCuotasAnioSeleccionados = lstMpCuotasAnioSeleccionados;
	}

	public HashMap<String, Integer> getMapMpCuotasAnioSeleccionados() {
		return mapMpCuotasAnioSeleccionados;
	}

	public void setMapMpCuotasAnioSeleccionados(HashMap<String, Integer> mapMpCuotasAnioSeleccionados) {
		this.mapMpCuotasAnioSeleccionados = mapMpCuotasAnioSeleccionados;
	}

	public List<FindCcLoteDetalleActo> getLstlotePreliminar() {
		return lstlotePreliminar;
	}

	public void setLstlotePreliminar(List<FindCcLoteDetalleActo> lstlotePreliminar) {
		this.lstlotePreliminar = lstlotePreliminar;
	}

	public BigDecimal getMontoMin() {
		return montoMin;
	}

	public void setMontoMin(BigDecimal montoMin) {
		this.montoMin = montoMin;
	}

	public BigDecimal getMontoMax() {
		return montoMax;
	}

	public void setMontoMax(BigDecimal montoMax) {
		this.montoMax = montoMax;
	}

	public HtmlComboBox getCmbHtmlOpUbicable() {
		return cmbHtmlOpUbicable;
	}

	public void setCmbHtmlOpUbicable(HtmlComboBox cmbHtmlOpUbicable) {
		this.cmbHtmlOpUbicable = cmbHtmlOpUbicable;
	}

	public String getSeleccionOp() {
		return seleccionOp;
	}

	public void setSeleccionOp(String seleccionOp) {
		this.seleccionOp = seleccionOp;
	}

	public Boolean getSoloPrimeraInscripcion() {
		return soloPrimeraInscripcion;
	}

	public void setSoloPrimeraInscripcion(Boolean soloPrimeraInscripcion) {
		this.soloPrimeraInscripcion = soloPrimeraInscripcion;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Boolean getOpParaUnaPersona() {
		return opParaUnaPersona;
	}

	public void setOpParaUnaPersona(Boolean opParaUnaPersona) {
		this.opParaUnaPersona = opParaUnaPersona;
	}
	
	public List<GnSector> getSectores() {
		return sectores;
	}

	public void setSectores(ArrayList<GnSector> sectores) {
		this.sectores = sectores;
	}
	

	public Integer getSectorID() {
		return sectorID;
	}

	public void setSectorID(Integer sectorID) {
		this.sectorID = sectorID;
	}
	
	public Boolean getPorSector() {
		return porSector;
	}

	public void setPorSector(Boolean porSector) {
		this.porSector = porSector;
	}
		
}
