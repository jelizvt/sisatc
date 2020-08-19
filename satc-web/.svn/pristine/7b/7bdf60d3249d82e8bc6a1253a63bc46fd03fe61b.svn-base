package com.sat.sisat.predial.managed;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.common.vo.Mensaje;
import com.sat.sisat.persistence.entity.DtFactorOfic;
import com.sat.sisat.persistence.entity.RjEstadoConservacion;
import com.sat.sisat.persistence.entity.RjMes;
import com.sat.sisat.persistence.entity.RjTipoDepreciacion;
import com.sat.sisat.persistence.entity.RpCategoriaObra;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.persistence.entity.RpMaterialPredominante;
import com.sat.sisat.persistence.entity.RpTipoObra;
import com.sat.sisat.persistence.entity.RpTipoObraPeriodo;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpTipoObraDTO;

@ManagedBean
@ViewScoped
public class OtrasInstalacionesManaged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4689253853513668683L;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	CalculoPredialBoRemote calculoPrediosBo;

	Mensaje mensaje = new Mensaje();

	private HtmlComboBox cmbTipoObra;
	private String cmbValueTipoObra;
	private List<SelectItem> lstTipoObra = new ArrayList<SelectItem>();
	private List<SelectItem> lstTipoObraIdsMinVivienda = new ArrayList<SelectItem>();
	private List<SelectItem> lstCategoriaObra = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipoObra = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRpTipoObra = new HashMap<Integer, String>();
	private HashMap<String, Integer> mapRpTipoObraIdMinVivienda = new HashMap<String, Integer>();

	private HashMap<String, Integer> mapRpCategoriaObra = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRpCategoriaObra = new HashMap<Integer, String>();

	private RpInstalacionDj instalacion;

	private HtmlComboBox cmbmes;
	private String cmbValueMes;
	private List<SelectItem> lstmes = new ArrayList<SelectItem>();
	private HashMap<String, String> mapRjMes = new HashMap<String, String>();
	private HashMap<String, String> mapIRjMes = new HashMap<String, String>();

	private HtmlComboBox cmbMaterialPredominante;
	private List<SelectItem> lstMaterialPredominante = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpMaterialPredominante = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRpMaterialPredominante = new HashMap<Integer, String>();
	private List<SelectItem> lstMaterialesPredominante = new ArrayList<SelectItem>();
	private String cmbValueMaterial;

	private HtmlComboBox cmbEstadoConservacion;
	private List<SelectItem> lstEstadoConservacion = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjEstadoConservacion = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRjEstadoConservacion = new HashMap<Integer, String>();

	private HtmlComboBox cmbTipoDepreciacion;
	private List<SelectItem> lstTipoDepreciacion = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjTipoDepreciacion = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRjTipoDepreciacion = new HashMap<Integer, String>();
	private String cmbValueDepreciacion;

	private Integer annoDj;

	private Boolean disabled;

	private String areaTerreno;
	private HtmlComboBox cmbPiso;
	private List<SelectItem> lstPiso = new ArrayList<SelectItem>();
	private String cmbValueCategoriaObra;
	private HtmlComboBox cmbCategoriaObra;
	private Integer codigoObra;
	private String medida;

	public OtrasInstalacionesManaged() {

	}

	@PostConstruct
	public void init() {
		try {
			Integer djId = (Integer) FacesUtil.getSessionMapValue(
					FacesContext.getCurrentInstance(), "NextDjId");
			if (djId != null && djId > 0) {
				RpDjpredial djpredio = registroPrediosBo.getRpDjpredial(djId);
				// se tomara el anho de la DJ
				// if(djpredio!=null&&djpredio.getAnnoDj()<=2012){
				// //Para instalaciones menores o iguales a 2012 se utiliza el
				// lsitado de instalaciones definidas para el 2012
				// setAnnoDj(2012);
				// }else{
				// setAnnoDj(djpredio.getAnnoDj());
				// }
				setAnnoDj(djpredio.getAnnoDj());
			} else {
				setAnnoDj(DateUtil.getAnioActual());
			}

			// SOLO SE TRABAJARA EN BASE A LA TABLA GENERADA EL AÑO 2013
			// --Categoria de obra
			// List<RpCategoriaObra>
			// lstRpCategoriaObra=registroPrediosBo.getAllRpCategoriaObra(getAnnoDj());
			List<RpCategoriaObra> lstRpCategoriaObra = registroPrediosBo
					.getAllRpCategoriaObra(2013);
			Iterator<RpCategoriaObra> it7 = lstRpCategoriaObra.iterator();
			lstCategoriaObra = new ArrayList<SelectItem>();
			while (it7.hasNext()) {
				RpCategoriaObra obj = it7.next();
				// obj.setDescripcion(obj.getDescripcion().concat(" - ").concat(obj.getPeriodo().toString()));
				lstCategoriaObra.add(new SelectItem(
						obj.getDescripcion().trim(), String.valueOf(obj
								.getCategoriaObraId())));
				mapRpCategoriaObra.put(obj.getDescripcion().trim(),
						obj.getCategoriaObraId());
				mapIRpCategoriaObra.put(obj.getCategoriaObraId(), obj
						.getDescripcion().trim());
			}

			// --mes
			List<RjMes> lstRjMes = registroPrediosBo.getAllRjMes();
			Iterator<RjMes> it9 = lstRjMes.iterator();
			lstmes = new ArrayList<SelectItem>();
			while (it9.hasNext()) {
				RjMes obj = it9.next();
				lstmes.add(new SelectItem(obj.getDescripcion().trim(), String
						.valueOf(obj.getMesId())));
				mapRjMes.put(obj.getDescripcion().trim(), obj.getMesId());
				mapIRjMes.put(obj.getMesId(), obj.getDescripcion().trim());
			}

			// RpMaterialPredominante		//Comentado en Abril 2017, Material Predominante de acuerdo al Informe 053-009-00001961.-Ini.
//			List<RpMaterialPredominante> lstRpMaterialPredominante = registroPrediosBo
//					.getAllRpMaterialPredominante();
//			Iterator<RpMaterialPredominante> it3 = lstRpMaterialPredominante
//					.iterator();
//			lstMaterialPredominante = new ArrayList<SelectItem>();
//
//			while (it3.hasNext()) {
//				RpMaterialPredominante obj = it3.next();
//				lstMaterialPredominante.add(new SelectItem(obj.getDescripcion()
//						.trim(), String.valueOf(obj.getMatPredominanteId())));
//				mapRpMaterialPredominante.put(obj.getDescripcion().trim(),
//						obj.getMatPredominanteId());
//				mapIRpMaterialPredominante.put(obj.getMatPredominanteId(), obj
//						.getDescripcion().trim());
//			}							//Comentado en Abril 2017, Material Predominante de acuerdo al Informe 053-009-00001961.-Fin

			// RjEstadoConservacion
			List<RjEstadoConservacion> lstRjEstadoConservacion = registroPrediosBo.getAllRjEstadoConservacion();
			Iterator<RjEstadoConservacion> it4 = lstRjEstadoConservacion.iterator();
			lstEstadoConservacion = new ArrayList<SelectItem>();

			while (it4.hasNext()) {
				RjEstadoConservacion obj = it4.next();
				lstEstadoConservacion.add(new SelectItem(obj.getDescripcion().trim(), String.valueOf(obj.getConservacionId())));
				mapRjEstadoConservacion.put(obj.getDescripcion().trim(),obj.getConservacionId());
				mapIRjEstadoConservacion.put(obj.getConservacionId(), obj.getDescripcion().trim());
			}

			// RjTipoDepreciacion
			List<RjTipoDepreciacion> lstRjTipoDepreciacion = registroPrediosBo.getAllRjTipoDepreciacion();
			setCmbValueDepreciacion(String.valueOf(lstRjTipoDepreciacion.get(0).getDescripcion()));
			lstTipoDepreciacion.add(new SelectItem(lstRjTipoDepreciacion.get(0).getDescripcion().trim(), String.valueOf(lstRjTipoDepreciacion.get(0).getTipoDepreciacionId())));
			mapRjTipoDepreciacion.put(lstRjTipoDepreciacion.get(0).getDescripcion(),lstRjTipoDepreciacion.get(0).getTipoDepreciacionId());
			//Iterator<RjTipoDepreciacion> it5 = lstRjTipoDepreciacion.iterator();	//Comentado en Abril 2017, Material Predominante de acuerdo al Informe 053-009-00001961.-Ini.
			//lstTipoDepreciacion = new ArrayList<SelectItem>();

			//while (it5.hasNext()) {
				//RjTipoDepreciacion obj = it5.next();
				//lstTipoDepreciacion.add(new SelectItem(obj.getDescripcion()	.trim(), String.valueOf(obj.getTipoDepreciacionId())));
				//mapRjTipoDepreciacion.put(obj.getDescripcion().trim(),obj.getTipoDepreciacionId());
				//mapIRjTipoDepreciacion.put(obj.getTipoDepreciacionId(), obj.getDescripcion().trim());
			//}																	
			//																		//Comentado en Abril 2017, Material Predominante de acuerdo al Informe 053-009-00001961.-Fin
			
			instalacion = new RpInstalacionDj();
			// --
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void salir() {

	}

	public void guardar() {
		try {
			Integer djId = (Integer) FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if (djId == null || djId == Constante.RESULT_PENDING) {
				RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
				registroPredio.inscripcionPredio();
			}
			djId = (Integer) FacesUtil.getSessionMapValue(
					FacesContext.getCurrentInstance(), "NextDjId");

			if (djId != null) {
				getInstalacion().setDjId(djId);
				// InstalacionId
				getInstalacion().setTipoObraId(mapRpTipoObra.get(getCmbValueTipoObra().trim()));
				// instalacion.annoInstalacion
				getInstalacion().setMesInstalacion(mapRjMes.get(getCmbValueMes().trim()));
				// instalacion.valorInstalacion
				getInstalacion().setMatPredominanteId(mapRpMaterialPredominante.get(cmbMaterialPredominante.getValue()));
				getInstalacion().setConservacionId(mapRjEstadoConservacion.get(cmbEstadoConservacion.getValue()));
				getInstalacion().setClasiDepreciacionId(mapRjTipoDepreciacion.get(cmbTipoDepreciacion.getValue()));
				getInstalacion().setEstado(Constante.ESTADO_ACTIVO);
				// getInstalacion().setValorInstalacion(getValorInstalacion(getInstalacion()));
				getInstalacion().setValorInstalacion(getValorInstalacion(getInstalacion(), getAnnoDj()));
				int result = 0;
				if (getInstalacion().getInstalacionId() != null	&& getInstalacion().getInstalacionId() > 0) {
					result = registroPrediosBo.actualizarRpInstalacionDj(getInstalacion());
				} else {
					result = registroPrediosBo.guardarRpInstalacionDj(getInstalacion());
				}
				if (result > 0) {
					RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
					if (registroPredio != null) {
						registroPredio.loadOtrasInstalaciones();
						if (!(getInstalacion().getInstalacionId() != null && getInstalacion().getInstalacionId() > 0))
							limpiar();
					}
				} else {
					WebMessages.messageError("No existe DJ seleccionado");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public BigDecimal getValorInstalacion(RpInstalacionDj instalacion,Integer periodo) {
		BigDecimal valorInstalacion = BigDecimal.ZERO;		   				BigDecimal valorUnitario = BigDecimal.ZERO;
		BigDecimal valorDepreciacion= BigDecimal.ZERO;						BigDecimal porcentaje= BigDecimal.valueOf(100.00);
		BigDecimal valorUnitarioOficializado = BigDecimal.ZERO;				BigDecimal valorUnitarioDepreciacion = BigDecimal.ZERO;
		try {
			RpTipoObra tipoObra = registroPrediosBo.getRpTipoObra(instalacion.getTipoObraId());
			// if(tipoObra!=null){
			// valorInstalacion=instalacion.getAreaTerreno().multiply(tipoObra.getValorUnitario());
			// }
			// SE SACARA EL VALOR DEL TIPO DE OBRA DE LA TABLA TIPO_OBRA_PERIODO
			if (tipoObra != null) {
				RpTipoObraPeriodo tipoObraPeriodo = new RpTipoObraPeriodo();
				tipoObraPeriodo = registroPrediosBo.getRpTipoObraPeriodo(instalacion.getTipoObraId(), periodo);
				//valorInstalacion = instalacion.getAreaTerreno().multiply(tipoObraPeriodo.getValorUnitario()); //Comentado en Abril 2017.
				
				/** De acuerdo al Informe 053-009-00001961, de fecha 04 Abril del 2017, el Valor de la Edificación está comprendida por "la sumatoria de los productos comprendidos por el área techada, 
				 *  el metrado de las obras complementarias y de las instalaciones fijas y permanentes, por  sus respectivos valores unitarios y los factores de depreciación". 
				 *  Y de acuerdo a la RESOLUCIÓN MINISTERIAL N° 373-2016-VIVIENDA Los precios unitarios consignados son a costo directo; para efectos del uso de estos valores, se deberá considerar en 
				 *  el cálculo el factor de oficialización = 0,68 y la depreciación respectiva. 
				 *  ((VU*0.68)* Deprec.)*Area=Valor: */
				 DtFactorOfic factorOficializacion=registroPrediosBo.getFactorOficializacion(periodo);
				 valorUnitarioOficializado=tipoObraPeriodo.getValorUnitario().multiply(factorOficializacion.getFactor());
				 getInstalacion().setValorUnitarioOficializado(valorUnitarioOficializado);
				 
				 int antiguedad = periodo - instalacion.getAnnoInstalacion();
				 valorDepreciacion=registroPrediosBo.obtenerPorcentajeDepreciacion(instalacion.getClasiDepreciacionId(), periodo, instalacion.getMatPredominanteId(), instalacion.getConservacionId(), antiguedad);
				 valorUnitarioDepreciacion=valorUnitarioOficializado.subtract(valorUnitarioOficializado.multiply((valorDepreciacion.divide(porcentaje))));
			    //valorInstalacion=valorUnitarioDepreciacion.multiply(instalacion.getAreaTerreno());//No se usará el Factor de Ofic. Cálculo, líneas abajo:
				 getInstalacion().setValorUnitarioDepreciado(valorUnitarioDepreciacion);
				 getInstalacion().setValorObra(valorUnitarioDepreciacion.multiply(instalacion.getAreaTerreno()));

				 /*No se utilizará "valorUnitarioOficializado",de acuerdo a los registros en dbo.dt_determinacion_instalacion y dbo.rp_instalacion_dj, sólo almacenan el valor de la inst. sin considerar el factor de oficialización.
				  * El cálculo es:*/
				 valorUnitario=instalacion.getAreaTerreno().multiply(tipoObraPeriodo.getValorUnitario());
				 valorInstalacion=valorUnitario.subtract(valorUnitario.multiply((valorDepreciacion.divide(porcentaje))));

				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return valorInstalacion;
	}

	public void loadDescripcionObraById() throws Exception {
		Integer categoriaObraId = getCodigoObra();
		if (categoriaObraId != null && categoriaObraId > 0) {
			List<FindRpTipoObraDTO> categoria = registroPrediosBo
					.getAllRpCaregoriaObraXIdMV(categoriaObraId);
			if (categoria != null && categoria.size() > 0) {
				// cmbCategoriaObra.setValue(String.valueOf(categoria.get(0)
				// .getDescripcion()));
				setCmbValueCategoriaObra(String.valueOf(categoria.get(0)
						.getDescripcion()));
				setMedida(categoria.get(0).getUnidadMedida());
				cargarListasTipoObra(categoria.get(0).getCategoriaObraId(),
						categoria.get(0).getIdMinVivienda());
			}
		} else {
			lstTipoObra = new ArrayList<SelectItem>();
			setCmbValueTipoObra("");
		}
	}

	public void selectCategoriaObra(ValueChangeEvent event) {
		try {
			setCodigoObra(0);
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			cmbValueCategoriaObra = String.valueOf(combo.getValue());
			if (cmbValueCategoriaObra != null
					&& cmbValueCategoriaObra.trim().length() > 0) {
				Integer categoriaObraId = mapRpCategoriaObra.get(String
						.valueOf(cmbValueCategoriaObra));

				if (categoriaObraId != null && categoriaObraId > 0) {
					RpCategoriaObra categoria = registroPrediosBo
							.getRpCategoriaObraById(categoriaObraId);
					if (categoria != null) {
						setMedida(categoria.getUnidad_medida());
						// setCodigoObra(categoriaObraId);
						cargarListasTipoObra(categoriaObraId, 0);
					}
				} else {
					lstTipoObra = new ArrayList<SelectItem>();
					setCmbValueTipoObra("");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectChangeTipoObra(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			cmbValueTipoObra = (String) combo.getValue();
			Integer val = mapRpTipoObraIdMinVivienda.get(cmbValueTipoObra);
			setCodigoObra(val);
			Integer obraId = mapRpTipoObra.get(String.valueOf(cmbValueTipoObra));
			cargarListasMaterialObra(obraId, annoDj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarListasMaterialObra(Integer tipoObraId,Integer periodo) {
		try {
		if(tipoObraId!= null)
		{
			List<RpMaterialPredominante> lstRpMaterialPredominante = registroPrediosBo.getMaterialPredominanteObra(tipoObraId, periodo);
			Iterator<RpMaterialPredominante> it3 = lstRpMaterialPredominante.iterator();
			lstMaterialesPredominante = new ArrayList<SelectItem>();

			while (it3.hasNext()) {
				RpMaterialPredominante obj = it3.next();
				lstMaterialesPredominante.add(new SelectItem(obj.getDescripcion().trim(), String.valueOf(obj.getMatPredominanteId())));
				mapRpMaterialPredominante.put(obj.getDescripcion().trim(),obj.getMatPredominanteId());
				mapIRpMaterialPredominante.put(obj.getMatPredominanteId(), obj.getDescripcion().trim());
				setCmbValueMaterial(obj.getDescripcion().trim());
				cmbMaterialPredominante.setValue(obj.getDescripcion().trim());
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void cargarListasTipoObra(Integer categoriaObraId,
			Integer codMinVivienda) {
		try {
			List<RpTipoObra> lstRpTipoObra = registroPrediosBo
					.getAllRpTipoObra(categoriaObraId, codMinVivienda);
			Iterator<RpTipoObra> it8 = lstRpTipoObra.iterator();
			lstTipoObra = new ArrayList<SelectItem>();
			lstTipoObraIdsMinVivienda = new ArrayList<SelectItem>();
			cmbTipoObra.setValue("");
			while (it8.hasNext()) {
				RpTipoObra obj = it8.next();
				lstTipoObra.add(new SelectItem(obj.getDescripcion(), String
						.valueOf(obj.getTipoObraId())));
				lstTipoObraIdsMinVivienda.add(new SelectItem(String.valueOf(obj
						.getDescripcion().trim()), String.valueOf(obj
						.getIdMinVivienda())));
				mapRpTipoObra.put(obj.getDescripcion().trim(),
						obj.getTipoObraId());
				mapIRpTipoObra.put(obj.getTipoObraId(), obj.getDescripcion()
						.trim());
				mapRpTipoObraIdMinVivienda.put(obj.getDescripcion().trim(),
						obj.getIdMinVivienda());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setProperty(FileUpload archivo) {
		getInstalacion().setReferencia(archivo.getFileName());
	}

	public void setParameters() {
		String paramParentFileUpload = FacesUtil
				.getRequestParameter("paramParentFileUpload");
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(),
				"paramParentFileUpload", paramParentFileUpload);
	}

	public void delete() {
		try {
			if (instalacion != null) {
				int result = registroPrediosBo.deleteRpInstalacionDj(
						instalacion.getDjId(), instalacion.getInstalacionId());
				if (result > 0) {
					RegistroPredioManaged registro = (RegistroPredioManaged) getManaged("registroPredioManaged");
					registro.loadOtrasInstalaciones();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void limpiar() {
		getCmbTipoObra().setValue("");
		getCmbTipoObra().setSelectFirstOnUpdate(Boolean.TRUE);
		instalacion = new RpInstalacionDj();
		setCmbValueMes("");
		setCmbValueCategoriaObra("");
		// getCmbmes().setValue("");
		// getCmbCategoriaObra().setValue("");
		getCmbMaterialPredominante().setValue("");
		setCmbValueTipoObra("");
		setCodigoObra(null);
		// getCmbEstadoConservacion().setValue("");
		// getCmbTipoDepreciacion().setValue("");
	}

	public void setProperty(RpInstalacionDj insta) {
		getCmbmes().setValue(insta.getMesInstalacion() == null ? null : mapIRjMes.get(insta.getMesInstalacion().trim()));
		getCmbCategoriaObra().setValue(insta.getCategoriaObraId() == null ? null : mapIRpCategoriaObra.get(insta.getCategoriaObraId()));
		if (insta.getCategoriaObraId() != null && insta.getCategoriaObraId() > Constante.RESULT_PENDING) {
			cargarListasTipoObra(insta.getCategoriaObraId(), 0);
		}
		 getCmbTipoObra().setValue(insta.getTipoObraId() == null ? null : mapIRpTipoObra.get(insta.getTipoObraId()));
		 //getCmbMaterialPredominante().setValue(insta.getMatPredominanteId()==null?null:mapIRpMaterialPredominante.get(insta.getMatPredominanteId()));
		 getCmbEstadoConservacion().setValue(insta.getConservacionId()==null?null:mapIRjEstadoConservacion.get(insta.getConservacionId()));
		 getCmbTipoDepreciacion().setValue(insta.getClasiDepreciacionId()==null?null:mapIRjTipoDepreciacion.get(insta.getClasiDepreciacionId()));
		 if(insta.getTipoObraId()!= null){
			  cargarListasMaterialObra(insta.getTipoObraId(),getAnnoDj());
		 }
		setInstalacion(insta);
	}

	public HtmlComboBox getCmbTipoObra() {
		return cmbTipoObra;
	}

	public void setCmbTipoObra(HtmlComboBox cmbTipoObra) {
		this.cmbTipoObra = cmbTipoObra;
	}

	public String getCmbValueTipoObra() {
		return cmbValueTipoObra;
	}

	public void setCmbValueTipoObra(String cmbValueTipoObra) {
		this.cmbValueTipoObra = cmbValueTipoObra;
	}

	public List<SelectItem> getLstTipoObra() {
		return lstTipoObra;
	}

	public void setLstTipoObra(List<SelectItem> lstTipoObra) {
		this.lstTipoObra = lstTipoObra;
	}

	public RpInstalacionDj getInstalacion() {
		return instalacion;
	}

	public void setInstalacion(RpInstalacionDj instalacion) {
		this.instalacion = instalacion;
	}

	public HtmlComboBox getCmbmes() {
		return cmbmes;
	}

	public void setCmbmes(HtmlComboBox cmbmes) {
		this.cmbmes = cmbmes;
	}

	public List<SelectItem> getLstmes() {
		return lstmes;
	}

	public void setLstmes(List<SelectItem> lstmes) {
		this.lstmes = lstmes;
	}

	public String getCmbValueMes() {
		return cmbValueMes;
	}

	public void setCmbValueMes(String cmbValueMes) {
		this.cmbValueMes = cmbValueMes;
	}

	public Boolean getIsEditable() {
		if (getInstalacion() != null
				&& getInstalacion().getInstalacionId() != null
				&& getInstalacion().getInstalacionId() > 0) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Integer getAnnoDj() {
		return annoDj;
	}

	public void setAnnoDj(Integer annoDj) {
		this.annoDj = annoDj;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public HtmlComboBox getCmbMaterialPredominante() {
		return cmbMaterialPredominante;
	}

	public void setCmbMaterialPredominante(HtmlComboBox cmbMaterialPredominante) {
		this.cmbMaterialPredominante = cmbMaterialPredominante;
	}

	public List<SelectItem> getLstMaterialPredominante() {
		return lstMaterialPredominante;
	}

	public void setLstMaterialPredominante(
			List<SelectItem> lstMaterialPredominante) {
		this.lstMaterialPredominante = lstMaterialPredominante;
	}

	public HashMap<String, Integer> getMapRpMaterialPredominante() {
		return mapRpMaterialPredominante;
	}

	public void setMapRpMaterialPredominante(
			HashMap<String, Integer> mapRpMaterialPredominante) {
		this.mapRpMaterialPredominante = mapRpMaterialPredominante;
	}

	public HashMap<Integer, String> getMapIRpMaterialPredominante() {
		return mapIRpMaterialPredominante;
	}

	public void setMapIRpMaterialPredominante(
			HashMap<Integer, String> mapIRpMaterialPredominante) {
		this.mapIRpMaterialPredominante = mapIRpMaterialPredominante;
	}

	public HtmlComboBox getCmbEstadoConservacion() {
		return cmbEstadoConservacion;
	}

	public void setCmbEstadoConservacion(HtmlComboBox cmbEstadoConservacion) {
		this.cmbEstadoConservacion = cmbEstadoConservacion;
	}

	public List<SelectItem> getLstEstadoConservacion() {
		return lstEstadoConservacion;
	}

	public void setLstEstadoConservacion(List<SelectItem> lstEstadoConservacion) {
		this.lstEstadoConservacion = lstEstadoConservacion;
	}

	public HashMap<String, Integer> getMapRjEstadoConservacion() {
		return mapRjEstadoConservacion;
	}

	public void setMapRjEstadoConservacion(
			HashMap<String, Integer> mapRjEstadoConservacion) {
		this.mapRjEstadoConservacion = mapRjEstadoConservacion;
	}

	public HashMap<Integer, String> getMapIRjEstadoConservacion() {
		return mapIRjEstadoConservacion;
	}

	public void setMapIRjEstadoConservacion(
			HashMap<Integer, String> mapIRjEstadoConservacion) {
		this.mapIRjEstadoConservacion = mapIRjEstadoConservacion;
	}

	public HtmlComboBox getCmbTipoDepreciacion() {
		return cmbTipoDepreciacion;
	}

	public void setCmbTipoDepreciacion(HtmlComboBox cmbTipoDepreciacion) {
		this.cmbTipoDepreciacion = cmbTipoDepreciacion;
	}

	public List<SelectItem> getLstTipoDepreciacion() {
		return lstTipoDepreciacion;
	}

	public void setLstTipoDepreciacion(List<SelectItem> lstTipoDepreciacion) {
		this.lstTipoDepreciacion = lstTipoDepreciacion;
	}

	public HashMap<String, Integer> getMapRjTipoDepreciacion() {
		return mapRjTipoDepreciacion;
	}

	public void setMapRjTipoDepreciacion(
			HashMap<String, Integer> mapRjTipoDepreciacion) {
		this.mapRjTipoDepreciacion = mapRjTipoDepreciacion;
	}

	public HashMap<Integer, String> getMapIRjTipoDepreciacion() {
		return mapIRjTipoDepreciacion;
	}

	public void setMapIRjTipoDepreciacion(
			HashMap<Integer, String> mapIRjTipoDepreciacion) {
		this.mapIRjTipoDepreciacion = mapIRjTipoDepreciacion;
	}

	public String getAreaTerreno() {
		return areaTerreno;
	}

	public void setAreaTerreno(String areaTerreno) {
		this.areaTerreno = areaTerreno;
	}

	public HtmlComboBox getCmbPiso() {
		return cmbPiso;
	}

	public void setCmbPiso(HtmlComboBox cmbPiso) {
		this.cmbPiso = cmbPiso;
	}

	public List<SelectItem> getLstPiso() {
		return lstPiso;
	}

	public void setLstPiso(List<SelectItem> lstPiso) {
		this.lstPiso = lstPiso;
	}

	public String getCmbValueCategoriaObra() {
		return cmbValueCategoriaObra;
	}

	public void setCmbValueCategoriaObra(String cmbValueCategoriaObra) {
		this.cmbValueCategoriaObra = cmbValueCategoriaObra;
	}

	public HtmlComboBox getCmbCategoriaObra() {
		return cmbCategoriaObra;
	}

	public void setCmbCategoriaObra(HtmlComboBox cmbCategoriaObra) {
		this.cmbCategoriaObra = cmbCategoriaObra;
	}

	public List<SelectItem> getLstCategoriaObra() {
		return lstCategoriaObra;
	}

	public void setLstCategoriaObra(List<SelectItem> lstCategoriaObra) {
		this.lstCategoriaObra = lstCategoriaObra;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public Integer getCodigoObra() {
		return codigoObra;
	}

	public void setCodigoObra(Integer codigoObra) {
		this.codigoObra = codigoObra;
	}

	public List<SelectItem> getLstTipoObraIdsMinVivienda() {
		return lstTipoObraIdsMinVivienda;
	}

	public void setLstTipoObraIdsMinVivienda(
			List<SelectItem> lstTipoObraIdsMinVivienda) {
		this.lstTipoObraIdsMinVivienda = lstTipoObraIdsMinVivienda;
	}

	public List<SelectItem> getLstMaterialesPredominante() {
		return lstMaterialesPredominante;
	}

	public void setLstMaterialesPredominante(
			List<SelectItem> lstMaterialesPredominante) {
		this.lstMaterialesPredominante = lstMaterialesPredominante;
	}

	public String getCmbValueMaterial() {
		return cmbValueMaterial;
	}

	public void setCmbValueMaterial(String cmbValueMaterial) {
		this.cmbValueMaterial = cmbValueMaterial;
	}

	public String getCmbValueDepreciacion() {
		return cmbValueDepreciacion;
	}

	public void setCmbValueDepreciacion(String cmbValueDepreciacion) {
		this.cmbValueDepreciacion = cmbValueDepreciacion;
	}
}
