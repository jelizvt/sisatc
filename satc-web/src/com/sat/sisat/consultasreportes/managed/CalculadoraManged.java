package com.sat.sisat.consultasreportes.managed;

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

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.determinacion.vehicular.business.DeterminacionVehicularBoRemote;
import com.sat.sisat.determinacion.vehicular.calculo.ImpuestoVehicular;
import com.sat.sisat.determinacion.vehicular.dto.DatosNecesariosDeterDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.DtTasaImpuestoPredial;
import com.sat.sisat.persistence.entity.RvCategoriaVehiculo;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculo;
import com.sat.sisat.persistence.entity.RvModeloVehiculoPK;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.dto.CalculoPredioDTO;
import com.sat.sisat.vehicular.business.VehicularBoRemote;

@ManagedBean
@ViewScoped
public class CalculadoraManged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5381442146770567109L;

	@EJB
	VehicularBoRemote vehicularBo;

	@EJB
	DeterminacionVehicularBoRemote determinaVehicularBo;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	CalculoPredialBoRemote calculoPredialBo;

	private List<SelectItem> lstClaseVehiculo = new ArrayList<SelectItem>();
	private String selectedClaseVehic;

	private List<SelectItem> lstCategoriaVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvCategoriaVehiculo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvCategoriaVehiculo = new HashMap<Integer, String>();
	private String selectedCategoriaVehic;

	private String selectedCondicion;

	private List<SelectItem> lstMarcaVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvMarca = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvMarca = new HashMap<Integer, String>();
	private String selectedMarcaVehic;

	private List<SelectItem> lstModelo = new ArrayList<SelectItem>();
	private HashMap<String, RvModeloVehiculoPK> mapRvModelo = new HashMap<String, RvModeloVehiculoPK>();
	private HashMap<RvModeloVehiculoPK, String> mapIRvModelo = new HashMap<RvModeloVehiculoPK, String>();
	private String selectedModeloVehic;

	private Date fechaInsc;
	private Date fechaAdq;

	private String anioAfecta;
	private String anioFabrica;
	private BigDecimal valorTrans;

	private BigDecimal baseImpon;
	private BigDecimal valorRef;
	private BigDecimal tasa;
	private BigDecimal factAjust;
	private BigDecimal imporImp;
	private BigDecimal valAjust;
	private BigDecimal valorTransRes;

	private HtmlComboBox cmbCondicionPredial;

	private ImpuestoVehicular iv;

	private HashMap<String, Integer> mapGnTipoCondicion = new HashMap<String, Integer>();

	public void cambioCondicionPredial(ValueChangeEvent event) {
		try {

			if (anioAfectaPredial == null
					|| anioAfectaPredial.trim().length() == 0) {
				WebMessages.messageError("Ingresar año de afectación");
				return;
			} else {
				anioAP = Integer.parseInt(anioAfectaPredial);
			}
			if (anioAP < 2006) {
				WebMessages.messageError("Año de Afectación no permitido");
				return;
			}

			String value = (String) event.getNewValue();

			try {
				valorUitB = generalBo.getUitAnio(anioAP).setScale(0,
						BigDecimal.ROUND_UP);

			} catch (Exception e) {
				WebMessages
						.messageError("No hay UIT para el Año de Afectación escogido");
				return;
			}

			int tipoCondicion = 0;

			if (value.length() > 0 && value != "--") {

				tipoCondicion = (Integer) mapGnTipoCondicion.get(value);
				System.out.println(tipoCondicion);
			}

			if (tipoCondicion == 1) {
				// baseImpoPredial= new BigDecimal(autovaluoPredial);
				baseImpoPredial = autovaluoPredial;
			} else if (tipoCondicion == 2) {
				baseImpoPredial = (autovaluoPredial).subtract((new BigDecimal(
						50)).multiply(valorUitB));
				if (baseImpoPredial.compareTo(new BigDecimal(0)) < 0) {
					baseImpoPredial = new BigDecimal(0);
				}
			} else if (tipoCondicion == 3) {
				baseImpoPredial = (autovaluoPredial).multiply(new BigDecimal(
						0.5));
			}

			setBaseImponiblePredial(baseImpoPredial.setScale(2,
					BigDecimal.ROUND_UP));

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageError("Error en la condicion");
		}
	}

	public void limpiarCalculo() {
		setBaseImpon(null);
		setValorRef(null);
		setTasa(null);
		setFactAjust(null);
		setValAjust(null);
		setImporImp(null);
		setValorTransRes(null);
	}

	public void limpiarCamposVehicular() {
		setFechaInsc(null);
		setAnioFabrica("");
		setValorTrans(null);
		setAnioAfecta("");
		setSelectedCategoriaVehic("");
		setSelectedMarcaVehic("");
		setSelectedModeloVehic("");

		limpiarCalculo();
	}

	public void calcularVehicular() {
		try {

			anioA = Integer.parseInt(anioAfecta.trim());
			anioF = Integer.parseInt(anioFabrica.trim());
			// validaFechaRegistro();

			int anioFechaIns = DateUtil.obtenerAnioSegunFecha(fechaInsc);
			int difeAnios = anioA - anioFechaIns;
			if (difeAnios > 3) {
				WebMessages
						.messageError("Fecha de Afectación tiene mas de 3 años");
				return;
			}
			if (difeAnios == 0) {
				WebMessages
						.messageError("Año de Afectación no puede ser igual año fecha Inscripción");
				return;
			}
			if (anioF == anioA) {
				WebMessages
						.messageError("Año de afectación no puede ser igual año Fabricación");
				return;
			}

			// BigDecimal valorAdqSol= new BigDecimal(valorTrans);
			BigDecimal valorAdqSol = valorTrans;

			BigDecimal porcProp = new BigDecimal("100.00");
			// DeterminacionVehicularDao deterVehicDao= new
			// DeterminacionVehicularDao();
			// obtener tasa
			getSelectedModeloVehic();
			// int id=mapRvModelo.get(getSelectedModeloVehic());

			RvModeloVehiculoPK id = mapRvModelo.get(getSelectedModeloVehic());
			id.getModeloVehiculoId();
			// DtTasaVehicular td=deterVehicDao.getTasaVehicular(anio);
			// tipoNotaria = (Integer) mapRpTipoNotaria.get(value);

			DatosNecesariosDeterDTO datos = new DatosNecesariosDeterDTO();
			datos.setAnioAfec(anioA);
			datos.setAnioFabric(anioF);
			datos.setModeloVehicId(id.getModeloVehiculoId());
			datos.setValorAdquiSoles(valorAdqSol);
			datos.setPorcentajePropiedad(porcProp);

			setIv(determinaVehicularBo.calcular(datos));

			setBaseImpon(iv.getBaseImponible());
			setValorRef(iv.getValoreRefencialMEF());
			setTasa(iv.getTasaImpuesto());
			setFactAjust(iv.getAjuste());
			setValAjust(iv.getValorAjuste());
			setImporImp(iv.getImpuestoTotal());
			setValorTransRes(valorTrans);

		} catch (SisatException e) {
			e.printStackTrace();
			WebMessages.messageError(e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private int anioA;
	private int anioF;

	private int anioAP;

	public String getUit0a15() {
		return uit0a15;
	}

	public void setUit0a15(String uit0a15) {
		this.uit0a15 = uit0a15;
	}

	public String getUit15a45() {
		return uit15a45;
	}

	public void setUit15a45(String uit15a45) {
		this.uit15a45 = uit15a45;
	}

	public String getUit45aMas() {
		return uit45aMas;
	}

	public void setUit45aMas(String uit45aMas) {
		this.uit45aMas = uit45aMas;
	}

	private String uit0a15;
	private String uit15a45;
	private String uit45aMas;

	private BigDecimal valorUit;

	private BigDecimal baseImpoPredial;

	private ArrayList<CalculoPredioDTO> listaCalculoPredioDTO = new ArrayList<CalculoPredioDTO>();

	private BigDecimal impuestoTotal;

	public ArrayList<CalculoPredioDTO> getListaCalculoPredioDTO() {
		return listaCalculoPredioDTO;
	}

	public void setListaCalculoPredioDTO(
			ArrayList<CalculoPredioDTO> listaCalculoPredioDTO) {
		this.listaCalculoPredioDTO = listaCalculoPredioDTO;
	}

	private BigDecimal autovaluoPredial;
	private BigDecimal valor15Uit;
	private BigDecimal valor60Uit;
	private BigDecimal montoMinimo;
	private BigDecimal valorUitB;

	public void limpiarCondicionBase() {
		setSelectedCondicion("");
		setBaseImponiblePredial(null);
	}

	public void limpiarPredial() {
		listaCalculoPredioDTO = new ArrayList<CalculoPredioDTO>();
		setAnioAfectaPredial("");
		setAutovaluoPredial(null);
		limpiarCondicionBase();
		setValorUit(null);
		setValor15Uit(null);
		setValor60Uit(null);
		setMontoMinimo(null);
		setImpuestoTotal(null);
	}

	public void calccularPredial() {

		listaCalculoPredioDTO = new ArrayList<CalculoPredioDTO>();
		BigDecimal impTotalB = new BigDecimal(0);

		baseImpoPredial = baseImponiblePredial;
		try {

			ArrayList<DtTasaImpuestoPredial> ListaTasaPredial = calculoPredialBo
					.getAllDeTasaImpuestoPredial(anioAP);

			setValor15Uit(ListaTasaPredial.get(1).getMontoInicio());
			setValor60Uit(ListaTasaPredial.get(2).getMontoInicio());
			setMontoMinimo(ListaTasaPredial.get(3).getMontoInicio());

			int nroUitsPredio = (baseImpoPredial.divide(valorUitB,
					RoundingMode.HALF_UP)).intValue();

			for (int i = 0; i < 3; i++) {

				CalculoPredioDTO calculoPredio = new CalculoPredioDTO();

				if (listaCalculoPredioDTO.size() == 0 && nroUitsPredio > 0
						&& nroUitsPredio >= 15) {
					calculoPredio.setTramo(ListaTasaPredial.get(i).getId()
							.getTramoId());
					calculoPredio.setMonto(ListaTasaPredial.get(i)
							.getMontoFin());
					calculoPredio.setTasa(ListaTasaPredial.get(i).getTasa());
					calculoPredio.setImpuesto(ListaTasaPredial.get(i)
							.getMontoTasa());

					nroUitsPredio = nroUitsPredio - 15;
					baseImpoPredial = baseImpoPredial.subtract(calculoPredio
							.getMonto());

					impTotalB = impTotalB.add(calculoPredio.getImpuesto())
							.setScale(2, BigDecimal.ROUND_UP);

				} else if (listaCalculoPredioDTO.size() == 0
						&& baseImpoPredial.intValue() >= 0
						&& nroUitsPredio < 15) {
					calculoPredio.setTramo(ListaTasaPredial.get(i).getId()
							.getTramoId());
					calculoPredio.setMonto(baseImpoPredial);
					calculoPredio.setTasa(ListaTasaPredial.get(i).getTasa());
					calculoPredio.setImpuesto(baseImpoPredial.multiply(
							ListaTasaPredial.get(i).getTasa()
									.divide(new BigDecimal(100))).setScale(2,
							BigDecimal.ROUND_UP));

					impTotalB = impTotalB.add(calculoPredio.getImpuesto());
					baseImpoPredial = new BigDecimal(0).setScale(2,
							BigDecimal.ROUND_UP);
				}

				if (listaCalculoPredioDTO.size() == 1 && nroUitsPredio >= 0
						&& nroUitsPredio >= 45) {
					calculoPredio.setTramo(ListaTasaPredial.get(i).getId()
							.getTramoId());
					calculoPredio.setMonto(ListaTasaPredial.get(i)
							.getMontoFin()
							.subtract(ListaTasaPredial.get(i).getMontoInicio())
							.setScale(2, BigDecimal.ROUND_UP));
					calculoPredio.setTasa(ListaTasaPredial.get(i).getTasa());
					calculoPredio.setImpuesto(ListaTasaPredial.get(i)
							.getMontoTasa().setScale(2, BigDecimal.ROUND_UP));

					nroUitsPredio = nroUitsPredio - 45;
					baseImpoPredial = baseImpoPredial.subtract(calculoPredio
							.getMonto());

					impTotalB = impTotalB.add(calculoPredio.getImpuesto());

				} else if (listaCalculoPredioDTO.size() == 1
						&& baseImpoPredial.intValue() >= 0
						&& nroUitsPredio < 45) {
					calculoPredio.setTramo(ListaTasaPredial.get(i).getId()
							.getTramoId());
					calculoPredio.setMonto(baseImpoPredial);
					calculoPredio.setTasa(ListaTasaPredial.get(i).getTasa());
					calculoPredio.setImpuesto(baseImpoPredial.multiply(
							ListaTasaPredial.get(i).getTasa()
									.divide(new BigDecimal(100))).setScale(2,
							BigDecimal.ROUND_UP));

					impTotalB = impTotalB.add(calculoPredio.getImpuesto());
					baseImpoPredial = new BigDecimal(0).setScale(2,
							BigDecimal.ROUND_UP);
				}

				if (listaCalculoPredioDTO.size() == 2
						&& baseImpoPredial.intValue() >= 0) {
					calculoPredio.setTramo(ListaTasaPredial.get(i).getId()
							.getTramoId());
					calculoPredio.setMonto(baseImpoPredial);
					calculoPredio.setTasa(ListaTasaPredial.get(i).getTasa());
					calculoPredio.setImpuesto((baseImpoPredial
							.multiply(ListaTasaPredial.get(i).getTasa()
									.divide(new BigDecimal(100)))).setScale(2,
							BigDecimal.ROUND_UP));

					impTotalB = impTotalB.add(calculoPredio.getImpuesto());
				}
				listaCalculoPredioDTO.add(calculoPredio);
			}

			impuestoTotal = impTotalB.setScale(2, BigDecimal.ROUND_UP);
			System.out.println(nroUitsPredio);
			setValorUit(valorUitB.setScale(2, BigDecimal.ROUND_UP));

		} catch (Exception e) {
			WebMessages.messageError("Error en el calculo");
			e.printStackTrace();
		}

	}

	private String anioAfectaPredial;
	private BigDecimal baseImponiblePredial;

	public void validaFechaRegistro() throws Exception {
		try {
			int anioFechaIns = DateUtil.obtenerAnioSegunFecha(fechaInsc);
			int difeAnios = anioA - anioFechaIns;
			if (difeAnios > 3) {
				WebMessages
						.messageError("fecha de afectación tiene mas de 3 años");
				return;
			}
			if (difeAnios == 0) {
				WebMessages
						.messageError("año de afectación no puede ser igual año fecha Inscripción");
				return;
			}
		} catch (Exception e) {
			throw e;

		}
	}

	private ArrayList<SelectItem> lstCondicionPredial = new ArrayList<SelectItem>();

	public ArrayList<SelectItem> getLstCondicionPredial() {
		return lstCondicionPredial;
	}

	public void setLstCondicionPredial(ArrayList<SelectItem> lstCondicionPredial) {
		this.lstCondicionPredial = lstCondicionPredial;
	}

	@PostConstruct
	public void init() {

		try {

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

			// datos predial
			mapGnTipoCondicion.put("Normal", 1);
			lstCondicionPredial.add(new SelectItem("Normal", "1"));
			mapGnTipoCondicion.put("Pensionista", 2);
			lstCondicionPredial.add(new SelectItem("Pensionista", "2"));
			mapGnTipoCondicion.put("Art. 18, incisos a) y c)", 3);
			lstCondicionPredial.add(new SelectItem("Art. 18, incisos a) y c)",
					"3"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private HtmlComboBox cmbTipoCategoria;

	public HtmlComboBox getCmbTipoCategoria() {
		return cmbTipoCategoria;
	}

	public void setCmbTipoCategoria(HtmlComboBox cmbTipoCategoria) {
		this.cmbTipoCategoria = cmbTipoCategoria;
	}

	public void changeCategoria(ActionEvent ev) {
		if (anioAfecta == null || anioAfecta.trim().length() == 0) {
			WebMessages.messageError("Ingresar año Afectación");
			return;
		}
		fillMarcaVehic();
		lstModelo = new ArrayList<SelectItem>();
		selectedModeloVehic = null;
	}

	private void fillMarcaVehic() {
		lstMarcaVehiculo = new ArrayList<SelectItem>();
		periodoAfecta = Integer.parseInt(anioAfecta);
		try {
			List<RvMarca> lst3 = new ArrayList<RvMarca>();
			int categId = -1;

			if (selectedCategoriaVehic != null) {
				categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
			}

			lst3 = vehicularBo.findRvMarca(categId, periodoAfecta);

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
			System.out.println("ERROR: " + ex);
		}
		selectedMarcaVehic = null;
	}

	public void changeMarca(ActionEvent ev) {
		fillModelo();
	}

	private Integer periodoAfecta;

	private void fillModelo() {
		lstModelo = new ArrayList<SelectItem>();

		Integer categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
		Integer marcaId = mapRvMarca.get(selectedMarcaVehic);
		periodoAfecta = Integer.parseInt(anioAfecta);

		try {
			if (categId != null && marcaId != null) {
				List<RvModeloVehiculo> lst = new ArrayList<RvModeloVehiculo>();
				// lst = vehicularBo.getAllRvModeloVehiculo(marcaId, categId);
				lst = vehicularBo.getAllRvModeloVehiculoByPeriodoAfecta(
						periodoAfecta, marcaId, categId);
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

	public List<SelectItem> getLstCategoriaVehiculo() {
		return lstCategoriaVehiculo;
	}

	public void setLstCategoriaVehiculo(List<SelectItem> lstCategoriaVehiculo) {
		this.lstCategoriaVehiculo = lstCategoriaVehiculo;
	}

	public String getSelectedCategoriaVehic() {
		return selectedCategoriaVehic;
	}

	public void setSelectedCategoriaVehic(String selectedCategoriaVehic) {
		this.selectedCategoriaVehic = selectedCategoriaVehic;
	}

	public List<SelectItem> getLstMarcaVehiculo() {
		return lstMarcaVehiculo;
	}

	public void setLstMarcaVehiculo(List<SelectItem> lstMarcaVehiculo) {
		this.lstMarcaVehiculo = lstMarcaVehiculo;
	}

	public String getSelectedMarcaVehic() {
		return selectedMarcaVehic;
	}

	public void setSelectedMarcaVehic(String selectedMarcaVehic) {
		this.selectedMarcaVehic = selectedMarcaVehic;
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

	public Date getFechaInsc() {
		return fechaInsc;
	}

	public void setFechaInsc(Date fechaInsc) {
		this.fechaInsc = fechaInsc;
	}

	public Date getFechaAdq() {
		return fechaAdq;
	}

	public void setFechaAdq(Date fechaAdq) {
		this.fechaAdq = fechaAdq;
	}

	public String getAnioAfecta() {
		return anioAfecta;
	}

	public void setAnioAfecta(String anioAfecta) {
		this.anioAfecta = anioAfecta;
	}

	

	public String getAnioFabrica() {
		return anioFabrica;
	}

	public void setAnioFabrica(String anioFabrica) {
		this.anioFabrica = anioFabrica;
	}

	public ImpuestoVehicular getIv() {
		return iv;
	}

	public void setIv(ImpuestoVehicular iv) {
		this.iv = iv;
	}



	public String getAnioAfectaPredial() {
		return anioAfectaPredial;
	}

	public void setAnioAfectaPredial(String anioAfectaPredial) {
		this.anioAfectaPredial = anioAfectaPredial;
	}

	public HtmlComboBox getCmbCondicionPredial() {
		return cmbCondicionPredial;
	}

	public void setCmbCondicionPredial(HtmlComboBox cmbCondicionPredial) {
		this.cmbCondicionPredial = cmbCondicionPredial;
	}

	public String getSelectedCondicion() {
		return selectedCondicion;
	}

	public void setSelectedCondicion(String selectedCondicion) {
		this.selectedCondicion = selectedCondicion;
	}

	public BigDecimal getValorTrans() {
		return valorTrans;
	}

	public void setValorTrans(BigDecimal valorTrans) {
		this.valorTrans = valorTrans;
	}

	public BigDecimal getValorTransRes() {
		return valorTransRes;
	}

	public void setValorTransRes(BigDecimal valorTransRes) {
		this.valorTransRes = valorTransRes;
	}

	public BigDecimal getBaseImpon() {
		return baseImpon;
	}

	public void setBaseImpon(BigDecimal baseImpon) {
		this.baseImpon = baseImpon;
	}

	public BigDecimal getValorRef() {
		return valorRef;
	}

	public void setValorRef(BigDecimal valorRef) {
		this.valorRef = valorRef;
	}

	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	public BigDecimal getFactAjust() {
		return factAjust;
	}

	public void setFactAjust(BigDecimal factAjust) {
		this.factAjust = factAjust;
	}

	public BigDecimal getImporImp() {
		return imporImp;
	}

	public void setImporImp(BigDecimal imporImp) {
		this.imporImp = imporImp;
	}

	public BigDecimal getValAjust() {
		return valAjust;
	}

	public void setValAjust(BigDecimal valAjust) {
		this.valAjust = valAjust;
	}

	public BigDecimal getAutovaluoPredial() {
		return autovaluoPredial;
	}

	public void setAutovaluoPredial(BigDecimal autovaluoPredial) {
		this.autovaluoPredial = autovaluoPredial;
	}

	public BigDecimal getBaseImponiblePredial() {
		return baseImponiblePredial;
	}

	public void setBaseImponiblePredial(BigDecimal baseImponiblePredial) {
		this.baseImponiblePredial = baseImponiblePredial;
	}

	public BigDecimal getValorUit() {
		return valorUit;
	}

	public void setValorUit(BigDecimal valorUit) {
		this.valorUit = valorUit;
	}

	public BigDecimal getValor15Uit() {
		return valor15Uit;
	}

	public void setValor15Uit(BigDecimal valor15Uit) {
		this.valor15Uit = valor15Uit;
	}

	public BigDecimal getValor60Uit() {
		return valor60Uit;
	}

	public void setValor60Uit(BigDecimal valor60Uit) {
		this.valor60Uit = valor60Uit;
	}

	public BigDecimal getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(BigDecimal montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public BigDecimal getImpuestoTotal() {
		return impuestoTotal;
	}

	public void setImpuestoTotal(BigDecimal impuestoTotal) {
		this.impuestoTotal = impuestoTotal;
	}

}
