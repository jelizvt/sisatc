package com.sat.sisat.consultasreportes.managed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.alcabala.BuscarAlcabalaDTO;
import com.sat.sisat.alcabala.ImprimirAlcabalaDTO;
import com.sat.sisat.alcabala.business.AlcabalaRemote;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.determinacion.vehicular.dto.DatosInafecDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.CdDeudaHistoricaPK;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.GnTipoCambio;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persistence.entity.RaAlcabalaSustento;
import com.sat.sisat.persistence.entity.RaAlcabalaTasa;
import com.sat.sisat.persistence.entity.RaDireccionAlcabala;
import com.sat.sisat.persistence.entity.RaDireccionAlcabalaHistorico;
import com.sat.sisat.persistence.entity.RaDjalcabala;
import com.sat.sisat.persistence.entity.RaDjalcabalaHistorico;
import com.sat.sisat.persistence.entity.RaTipoTransferencia;
import com.sat.sisat.persistence.entity.RaTransferenteAlcabala;
import com.sat.sisat.persistence.entity.RpCondicionPropiedad;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.predial.dto.ListRpDjPredial;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class SimuladorAlcabalaManaged extends BaseManaged {

	@EJB
	AlcabalaRemote alcabalaBo;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;

	@EJB
	PersonaBoRemote personaBo;

	
	public SimuladorAlcabalaManaged(){
		getSessionManaged().setLinkRegresar("/sisat/consultasreportes/simuladorAlcabala.xhtml");

	}
	
	private HtmlComboBox cmbUbicacion;
	private String tipoUbicacionPropiedad1;
	private static final long serialVersionUID = -4978860544238485506L;
	private String iPMmesAnte;
	private String iPMdicAnte;
	private String factorAjuste;
	private String tipoCambio;
	private boolean isFueraDistrito;
	private boolean isDistrital;
	private List<RaAlcabalaSustento> lstAnexos = new ArrayList<RaAlcabalaSustento>();
	private String direccionPredio;
	private Date fechaTransferencia;
	private Date fechaDeclaracion;
	private ArrayList<ListRpDjPredial> records;
	private FindRpDjPredial findRpDjPredial;
	private String DjPredio;
	private String autovaluo;
	private String valorTransferencia;
	private String autovaluoAfecta;
	private String valorTransferenciaSoles;
	private String ajuste;
	private String autovaluoAjuste;
	private String valorUIT;
	private String nroUitDeduc;
	private String nroFormulario;
	private Boolean botonActualiza;
	
	private String cmbTipoTransferenciaValor;
	private String cmbTipoMonedaValor;

	private String textoIPM1;
	private String textoIPM2;
	
	private String usuario;
	private Integer personaId;
	private Integer cmbPredioId;
	private List<SelectItem> lstSelectItemPredios = new ArrayList<SelectItem>();
	private ArrayList<FindRpDjPredial> lstPredios;
	private List<Integer> lstPrediosId = new ArrayList<Integer>();
	private BigDecimal porcentaje;
	
	private HtmlComboBox cmbTipoMoneda;
	
	private String mayorValorComparado;
	private String valorDeduccion;
	private String baseImponible;
	private String baseExonerada;
	private String baseAfecta;
	private String tasa;
	private String impuestoPagar;
	private String interesMora;
	private String totalPagar;
	private String departamento;
	private String provincia;
	private String distrito;
	private String tipoVia;
	private String nombreVia;
	private String nro1;
	private String letra1;
	private String porcPropiedad;
	
	private List<SelectItem> lstTipoMoneda = new ArrayList<SelectItem>();

	private HashMap<String, Integer> mapGnTipoMoneda = new HashMap<String, Integer>();
	private boolean primeraVEnta;
	private boolean ubicacionPredio;
	private int selectedTipoPred = 1;

	@PostConstruct
	public void init() {
		try {
			porcentaje = new BigDecimal(100);
			datosInafec=alcabalaBo.getInafecAlcabala(getSessionManaged().getContribuyente().getPersonaId());
			if(datosInafec!=null){
				setInafectoImpuesto(Boolean.TRUE);
			}else{
				setInafectoImpuesto(Boolean.FALSE);
			}
			
			setBotonGuarda(Boolean.TRUE);
			setPorcPropiedad("100.00");
			setDistrital(true);
			setFueraDistrito(false);

			// Generando Id de alcabala
			setDjAlcabala(String.valueOf(traeIdAlcabala()));

			// TRAYENDO TIPO PERSONA
			List<MpTipoPersona> lstMpTipoPersona = personaBo.getAllMpTipoPersona();
			Iterator<MpTipoPersona> it4 = lstMpTipoPersona.iterator();

			while (it4.hasNext()) {
				MpTipoPersona obj = it4.next();
				lstTipoPersona.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getTipoPersonaId())));
			}

			// TRAYENDO CONDICION DE PROPIEDAD
			List<RpCondicionPropiedad> lstRpCondicionPropiedad = registroPrediosBo.getAllRpCondicionPropiedad();
			Iterator<RpCondicionPropiedad> it3 = lstRpCondicionPropiedad.iterator();
			while (it3.hasNext()) {
				RpCondicionPropiedad obj = it3.next();
				lstCondicionPropiedad.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getCondicionPropiedadId())));
				mapRpCondicionPropiedad.put(obj.getDescripcion(),obj.getCondicionPropiedadId());
			}

			// CARGA TIPO DE MONEDA
			List<GnTipoMoneda> lstTm = alcabalaBo.getAllGnTipoMoneda();
			Iterator<GnTipoMoneda> itTm = lstTm.iterator();
			while (itTm.hasNext()) {
				GnTipoMoneda objTm = itTm.next();
				lstTipoMoneda.add(new SelectItem(objTm.getDescripcion(), String.valueOf(objTm.getTipoMonedaId())));
				mapGnTipoMoneda.put(objTm.getDescripcion(),	objTm.getTipoMonedaId());
			}
			setCmbTipoMonedaValor(lstTipoMoneda.get(0).getDescription());
			//USUARIO
			usuario = getSessionManaged().getUsuarioLogIn().getNombreUsuario();

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void buscarPredios(){
		try {
			
			if(getPersonaId() != null){
				limpiar();
				lstPredios = registroPrediosBo.getRpDjpredial(null,null,null,null,null,null,null,null,null,null,null,personaId,Boolean.TRUE);
					
				if(lstPredios != null && lstPredios.size()>0){
					for(FindRpDjPredial predio : lstPredios){
						lstPrediosId.add(predio.getPredioId());
						lstSelectItemPredios.add(new SelectItem(predio.getPredioId()));						
					}					
					cmbPredioId = lstPrediosId.get(0);
					predioId = lstPrediosId.get(0);
				}
				
			}else{
				addErrorMessage("Debe ingresar el Código de Contribuyente");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void limpiar(){
		lstPredios = new ArrayList<FindRpDjPredial>();
		lstPrediosId = new ArrayList<Integer>();
		lstSelectItemPredios  = new ArrayList<SelectItem>();
		cmbPredioId = null;
	}
	public void setProperty(FindRpDjPredial currentItem){
		setDjPredio(String.valueOf(currentItem.getDjId()));
		setDireccionPredio(currentItem.getDireccionCompleta());
		setPorcPropiedad(String.valueOf(currentItem.getPorcPropiedad()));
		setDireccionPredio(String.valueOf(currentItem.getDireccionCompleta()));
		
		setPredioId(currentItem.getPredioId());
				
		if(currentItem.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)){
			setSelectedTipoPred(1);
		}else {
			setSelectedTipoPred(2);
		}
		
		otenerAutovaluo();

	}

	
	public void setProperty(BuscarAlcabalaDTO currentItem){
		setDjAlcabala(""+currentItem.getDjAlcabalaId());
		setDireccionPredio(currentItem.getDirecCompleta());
		setDjPredio(""+currentItem.getDjId());	
		setFechaTransferencia(DateUtil.convertStringToDate(currentItem.getFechaTransferencia()));
		setPorcPropiedad(String.valueOf(currentItem.getPorcPropiedad()));
		setFechaDeclaracion(currentItem.getFechaDeclaracion());		
		setUsuario(currentItem.getUsuario());
		setBotonActualiza(Boolean.TRUE);
		
		motivoActualizacion=2;
		
		
		//tipo predio
		if(currentItem.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)){
			selectedTipoPred=1;
		}else {
			selectedTipoPred=2;
		}
		


		//tipoMoneda
		Iterator itMoneda = mapGnTipoMoneda.entrySet().iterator();
//		System.out.println(currentItem.getTipoMonedaId());
		while (itMoneda.hasNext()) {
			Map.Entry e=(Map.Entry)itMoneda.next();
			if((Integer)e.getValue()==currentItem.getTipoMonedaId()){
				setCmbTipoMonedaValor(String.valueOf(e.getKey()));
			}
		}
		
		//trayendo direccion
		try {
			
			RaDireccionAlcabala raDirec= alcabalaBo.getRaDireccionAlcabalaByDjAlcabalaId(currentItem.getDjAlcabalaId(), currentItem.getDireccionAlcabalaId());
			
			setRaDirecAnterior(raDirec);
			
			//getDireccionAlcabalaManaged().setRaDireccionAlcabala(raDirec);
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
		
		
		tipoMonedaId=currentItem.getTipoMonedaId();
		setAutovaluo(String.valueOf(currentItem.getAutovaluoTotal()));
		setValorTransferencia(String.valueOf(currentItem.getValorTransferencia()));

		try {
			Date datePeriodo= new Date(currentItem.getFechaDeclaracion().getTime());
			
			setDtDeterminacion(alcabalaBo.getAllDtDeterminacion(currentItem.getPersonaId(),DateUtil.obtenerAnioSegunFecha(datePeriodo), Constante.CONCEPTO_ALCABALA, Constante.ESTADO_ACTIVO));
				
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
	}
	
	private ArrayList<DtDeterminacion> dtDeterminacion;
	private ArrayList<BuscarPersonaDTO> lstTransferentesAnterior= new ArrayList<BuscarPersonaDTO>();
	private RaDireccionAlcabala raDirecAnterior= new RaDireccionAlcabala();
	
	public RaDireccionAlcabala getRaDirecAnterior() {
		return raDirecAnterior;
	}

	public void setRaDirecAnterior(RaDireccionAlcabala raDirecAnterior) {
		this.raDirecAnterior = raDirecAnterior;
	}

	public void checkUbicacionPredio(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setUbicacionPredio(Boolean.TRUE);

		} else {
			setUbicacionPredio(Boolean.FALSE);
		}
	}


	private List<SelectItem> lstCondicionPropiedad = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpCondicionPropiedad = new HashMap<String, Integer>();
	private List<SelectItem> lstTipoPersona = new ArrayList<SelectItem>();
	private List<SelectItem> lstsubtipopersona = new ArrayList<SelectItem>();

	public void checkPrimeraVenta(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setPrimeraVEnta(Boolean.TRUE);
		} else {
			setPrimeraVEnta(Boolean.FALSE);
		}
	}

	public void changeListenerComboBoxPredio(ValueChangeEvent event) {

		Integer cmbValueSelect = (Integer) event.getNewValue();

		if (cmbValueSelect != null && cmbValueSelect > 0) {
			predioId=cmbValueSelect;
		}
	}
	
	public String getAutovaluo() {
		return autovaluo;
	}

	public void setAutovaluo(String autovaluo) {
		this.autovaluo = autovaluo;
	}

	public String getValorTransferencia() {
		return valorTransferencia;
	}

	public void setValorTransferencia(String valorTransferencia) {
		this.valorTransferencia = valorTransferencia;
	}

	public String getValorTransferenciaSoles() {
		return valorTransferenciaSoles;
	}

	public void setValorTransferenciaSoles(String valorTransferenciaSoles) {
		this.valorTransferenciaSoles = valorTransferenciaSoles;
	}

	public String getAjuste() {
		return ajuste;
	}

	public void setAjuste(String ajuste) {
		this.ajuste = ajuste;
	}

	public String getAutovaluoAjuste() {
		return autovaluoAjuste;
	}

	public void setAutovaluoAjuste(String autovaluoAjuste) {
		this.autovaluoAjuste = autovaluoAjuste;
	}

	public String getValorUIT() {
		return valorUIT;
	}

	public void setValorUIT(String valorUIT) {
		this.valorUIT = valorUIT;
	}

	public String getNroUitDeduc() {
		return nroUitDeduc;
	}

	public void setNroUitDeduc(String nroUitDeduc) {
		this.nroUitDeduc = nroUitDeduc;
	}

	public void setAutovaluoAfecta(String autovaluoAfecta) {
		this.autovaluoAfecta = autovaluoAfecta;
	}

	public String getBaseAfecta() {
		return baseAfecta;
	}

	public void setBaseAfecta(String baseAfecta) {
		this.baseAfecta = baseAfecta;
	}

	public String getTasa() {
		return tasa;
	}

	public void setTasa(String tasa) {
		this.tasa = tasa;
	}

	public String getImpuestoPagar() {
		return impuestoPagar;
	}

	public void setImpuestoPagar(String impuestoPagar) {
		this.impuestoPagar = impuestoPagar;
	}

	public String getInteresMora() {
		return interesMora;
	}

	public void setInteresMora(String interesMora) {
		this.interesMora = interesMora;
	}

	public String getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(String totalPagar) {
		this.totalPagar = totalPagar;
	}

	public String getAutovaluoAfecta() {
		return autovaluoAfecta;
	}

	public String getDjPredio() {
		return DjPredio;
	}

	public void setDjPredio(String djPredio) {
		DjPredio = djPredio;
	}

	public FindRpDjPredial getFindRpDjPredial() {
		return findRpDjPredial;
	}

	public void setFindRpDjPredial(FindRpDjPredial findRpDjPredial) {
		this.findRpDjPredial = findRpDjPredial;
	}

	public ArrayList<ListRpDjPredial> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<ListRpDjPredial> records) {
		this.records = records;
	}
	
	private Boolean botonGuarda;
	private Boolean inafectoImpuesto;
	private DatosInafecDTO datosInafec= new DatosInafecDTO();
	
	

	public Integer tipoMonedaId;
	public Integer condicionPropiedadId;
	public Integer tipoTransferencia;

	

	public void loadCondicionPropiedad(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();
			if (value.length()>0&&value!="--") {
				condicionPropiedadId = (Integer) mapRpCondicionPropiedad.get(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadMonedaById(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();
			if (value.length()>0&&value!="--") {
				tipoMonedaId = (Integer) mapGnTipoMoneda.get(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	
	public void limpiarCalculo(){
		setPrimeraVEnta(Boolean.FALSE);

		setAutovaluo("");
		setAutovaluoAjuste("");
		setValorTransferencia(null);
		setValorUIT(null);
		setFactorAjuste(null);
		setTipoCambio(null);
		setTextoIPM1(null);
		setiPMmesAnte(null);
		setAjuste(null);
		setValorTransferenciaSoles(null);

		setTextoIPM2(null);
		setiPMdicAnte(null);
		setNroUitDeduc(null);
		
		setMayorValorComparado(null);
		setValorDeduccion(null);
		setBaseImponible(null);
		setBaseExonerada(null);
		setBaseAfecta(null);
		setTasa(null);
		setImpuestoPagar(null);
		setInteresMora(null);
		setTotalPagar(null);
		
		
	}
	
	private BigDecimal valorTransferenciaSolesB = new BigDecimal(0);
	
	public void obtieneValorTransferencia()throws Exception{
		// para tipo moneda soles
		if (tipoMonedaId == 1 && valorTransferencia != null) {

			valorTransferenciaSolesB = new BigDecimal(valorTransferencia);
			setValorTransferenciaSoles(String.valueOf(valorTransferenciaSolesB));

			setTipoCambio("1.00");

			// para tipo de moneda dolares
		} else if (tipoMonedaId == 2 && valorTransferencia != null) {
			//cambio dolares dia
			GnTipoCambio gnTipoCambio= new GnTipoCambio();
			try {
				gnTipoCambio=generalBo.obtenerTipoCambioDia(2);	
				
				setCambioDolares(gnTipoCambio.getValorCompra());
				
				valorTransferenciaSolesB = new BigDecimal(valorTransferencia).multiply(cambioDolares);
				setValorTransferenciaSoles(String.valueOf(valorTransferenciaSolesB.setScale(2,BigDecimal.ROUND_HALF_UP)));
				setTipoCambio(String.valueOf(cambioDolares));
			
			} catch (Exception e) {
				WebMessages.messageError("Tipo cambio no disponible");
				throw e;
			}
		} 
	}
	
	
	private Integer predioId; 
	

	public void otenerAutovaluo(){
	try {
		
		if( (predioId != null && predioId > 0) && (getFechaTransferencia()!=null) && (personaId != null && personaId > 0)){
			
			SimpleDateFormat simpleDateformatAnio = new SimpleDateFormat("yyyy");
			int anioTransfer = Integer.parseInt(simpleDateformatAnio.format(fechaTransferencia));
			
			if(porcentaje.compareTo(new BigDecimal(100)) == 0){
				setAutovaluo(String.valueOf(alcabalaBo.obtenerAutovaluoPredio(predioId, anioTransfer)));
				
			}else if(porcentaje.compareTo(new BigDecimal(0)) > 0 && porcentaje.compareTo(new BigDecimal(100)) < 0){
				BigDecimal autovaluo = (alcabalaBo.obtenerAutovaluoPredio(predioId, anioTransfer));
				autovaluo = autovaluo.multiply(porcentaje).divide( new BigDecimal(100), 2, RoundingMode.HALF_UP );
				autovaluo = autovaluo.setScale(2, RoundingMode.HALF_UP);
				setAutovaluo(String.valueOf(autovaluo));
			}else{
				addErrorMessage("El porcentaje es inconsistente");
			}
		}else{
			addErrorMessage("Datos incompletos para realizar el cálculo");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		WebMessages.messageFatal(e);		
		}
	}
	

	public void realizarCalculo() {

		try {
			//Valor de Autovaluo
			setAutovaluoAfecta(autovaluo);
			
			//Valor de la Transferencia
			obtieneValorTransferencia();
			
			//Valor UIT
			BigDecimal valorUITB = generalBo.getUitAnio(DateUtil.obtenerAnioSegunFecha(fechaTransferencia));//new BigDecimal(3500.00);
			setValorUIT(String.valueOf(valorUITB));

			SimpleDateFormat simpleDateformatAnio = new SimpleDateFormat("yyyy");
			SimpleDateFormat simpleDateformatMes = new SimpleDateFormat("MM");

			int anioIpmMesAnte = Integer.parseInt(simpleDateformatAnio.format(fechaTransferencia));
			int mesIpmMesAnte = Integer.parseInt(simpleDateformatMes.format(fechaTransferencia));

			BigDecimal ipmMesAnte = new BigDecimal(0);
			BigDecimal ipmDIcAnte = new BigDecimal(0);
			
			int anioIpmMesDicAnte=anioIpmMesAnte-1;
			int mesIpmMesDicAnte=12;
			
			if(mesIpmMesAnte==1){
				anioIpmMesAnte=anioIpmMesAnte-1;
				mesIpmMesAnte=12;
				
			}else{
				mesIpmMesAnte=mesIpmMesAnte-1;
			}
			
			ipmMesAnte = alcabalaBo.getIPM(anioIpmMesAnte, mesIpmMesAnte);
			setiPMmesAnte(String.valueOf(ipmMesAnte.setScale(5, BigDecimal.ROUND_UP)));
			ipmDIcAnte = alcabalaBo.getIPM(anioIpmMesDicAnte, mesIpmMesDicAnte);
			setiPMdicAnte(String.valueOf(ipmDIcAnte.setScale(5, BigDecimal.ROUND_UP)));
		
			if(mesIpmMesAnte==12){
			setTextoIPM1("Diciembre "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==11){
			setTextoIPM1("Noviembre "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==10){
			setTextoIPM1("Octubre "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==9){
			setTextoIPM1("Setiembre "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==8){
			setTextoIPM1("Agosto "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==7){
			setTextoIPM1("Julio "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==6){
			setTextoIPM1("Junio "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==5){
			setTextoIPM1("Mayo "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==4){
			setTextoIPM1("Abril "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==3){
			setTextoIPM1("Marzo "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==2){
			setTextoIPM1("Febrero "+anioIpmMesAnte);	
			}else if(mesIpmMesAnte==1){
			setTextoIPM1("Enero "+anioIpmMesAnte);//ojo dice setTextoIPM1("Enenero "+anioIpmMesAnte); 	
			}
			
			setTextoIPM2(""+(anioIpmMesDicAnte));
		
			// factor de ajuste
			BigDecimal ajusteB = ipmMesAnte.divide(ipmDIcAnte, 5,RoundingMode.HALF_UP);

			setFactorAjuste(String.valueOf(ajusteB));

			// AUTOVALUO CON AJUSTE
			BigDecimal autovaluoajusteB = new BigDecimal(0);
			BigDecimal autovaluoB = new BigDecimal(autovaluo);

			autovaluoajusteB = (autovaluoB.multiply(ajusteB)).setScale(2,BigDecimal.ROUND_HALF_UP);
			setAutovaluoAjuste(String.valueOf(autovaluoajusteB));

			// AJUSTE
			BigDecimal autovalAjus2 = new BigDecimal(autovaluoAjuste);
			BigDecimal autovaluo2 = new BigDecimal(autovaluo);

			BigDecimal ajuste2 = (autovalAjus2.subtract(autovaluo2)).setScale(2, BigDecimal.ROUND_HALF_UP);
			setAjuste(String.valueOf(ajuste2));

			//obteniendo tasa y UIT
			RaAlcabalaTasa raAlcaTasa=alcabalaBo.getTasa(3);
			
			BigDecimal tasaB = raAlcaTasa.getTasa();
			
			// VALOR DE DEDUCCION
			int nroUIT =  raAlcaTasa.getNroMaximoUit();
			setNroUitDeduc(String.valueOf(nroUIT));

			
			// MAYOR VALOR COMPARADO
			BigDecimal mayorValorComparadoB = new BigDecimal(0);
			if (valorTransferenciaSolesB.compareTo(autovaluoajusteB) >= 0) {
				mayorValorComparadoB = valorTransferenciaSolesB;
			} else if (autovaluoajusteB.compareTo(valorTransferenciaSolesB) > 0) {
				mayorValorComparadoB = autovaluoajusteB;
			}
			setMayorValorComparado(String.valueOf(mayorValorComparadoB.setScale(2,BigDecimal.ROUND_UP)));

			// DEDUCCION
			BigDecimal valorDeduccionB = new BigDecimal(0);
			valorDeduccionB = valorUITB.multiply(new BigDecimal(nroUIT));
			setValorDeduccion(String.valueOf(valorDeduccionB.setScale(2,BigDecimal.ROUND_UP)));

			BigDecimal baseExoneradaB = new BigDecimal(0);
			BigDecimal baseImponibleB = new BigDecimal(0);
			BigDecimal baseAfectaB = new BigDecimal(0);
			

			// BASE IMPONIBLE
			if (primeraVEnta == true) {
				baseImponibleB = autovaluoajusteB.subtract(valorDeduccionB);
				baseExoneradaB = valorTransferenciaSolesB;
			} else if (primeraVEnta == false) {

				baseImponibleB = mayorValorComparadoB.subtract(valorDeduccionB);
			}
			if (baseImponibleB.compareTo(new BigDecimal(0)) <= 0) {
				baseImponibleB = new BigDecimal(0);
			}
			
			setBaseImponible(String.valueOf(baseImponibleB.setScale(2,BigDecimal.ROUND_UP)));
			setBaseExonerada(String.valueOf(baseExoneradaB.setScale(2,BigDecimal.ROUND_UP)));
			baseAfectaB = baseImponibleB.subtract(baseExoneradaB);
			
			if(baseAfectaB.compareTo(new BigDecimal(0))<= 0){
				baseAfectaB = new BigDecimal(0);
			}
			setBaseAfecta(String.valueOf(baseAfectaB.setScale(2,BigDecimal.ROUND_UP)));
			setTasa(String.valueOf(tasaB.multiply(new BigDecimal("100"))));
			BigDecimal impuestoPagarB = new BigDecimal(0);
			BigDecimal totalPagar = new BigDecimal(0);
			impuestoPagarB = baseAfectaB.multiply(tasaB);
			setImpuestoPagar(String.valueOf(impuestoPagarB.setScale(2,BigDecimal.ROUND_UP)));
			BigDecimal interesMoraB = new BigDecimal(0);
			Calendar calen = Calendar.getInstance();
			calen.setTime(fechaTransferencia);
			calen.add(Calendar.MONTH, 1);
			int mesVence = calen.get(Calendar.MONTH) + 1;
			int diaVence = calen.getActualMaximum(Calendar.DAY_OF_MONTH);
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			String fechaVence = "" + DateUtil.obtenerAnioSegunFecha(fechaTransferencia) + "-" + mesVence + "-"+ diaVence + "";
			fechaVenceD = formatoFecha.parse(fechaVence);
			
			// FECHA CONSULTA
			Date fechaConsulta = new Date();
			// invoca servicio para interesMoratorioB
			interesMoraB = generalBo.obtenerInteresSimple(impuestoPagarB, null,	fechaVenceD, fechaConsulta);
			setInteresMora(String.valueOf(interesMoraB.setScale(2,BigDecimal.ROUND_UP)));

			// total a pagar
			totalPagar = impuestoPagarB.add(interesMoraB);
			setTotalPagar(String.valueOf(totalPagar.setScale(2,BigDecimal.ROUND_UP)));

			setCalculado(Boolean.TRUE);
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageError("Error en calculo");
			}
	}
	
	private Date fechaVenceD;
	private Boolean calculado;
	
	public void calcularInteres(Date fechaConsulta){
		try {
			
			//obteniendo tasa y UIT
			RaAlcabalaTasa raAlcaTasa=alcabalaBo.getTasa(3);
			BigDecimal tasaB = raAlcaTasa.getTasa();
			BigDecimal totalPagar = new BigDecimal(0);
			BigDecimal baseAfectaB= new BigDecimal(getBaseAfecta());
			BigDecimal impuestoPagarB = baseAfectaB.multiply(tasaB);
			Calendar calen = Calendar.getInstance();
			calen.setTime(fechaTransferencia);
			calen.add(Calendar.MONTH, 1);
			int mesVence = calen.get(Calendar.MONTH) + 1;
			int diaVence = calen.getActualMaximum(Calendar.DAY_OF_MONTH);
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			String fechaVence = "" + DateUtil.obtenerAnioSegunFecha(fechaTransferencia) + "-" + mesVence + "-"+ diaVence + "";
			Date fechaVenceD = formatoFecha.parse(fechaVence);
			
			BigDecimal interesMoraB = new BigDecimal(0);
			interesMoraB = generalBo.obtenerInteresSimple(impuestoPagarB, null,	fechaVenceD, fechaConsulta);
			setInteresMora(String.valueOf(interesMoraB.setScale(2,BigDecimal.ROUND_UP)));

			// total a pagar
			totalPagar = impuestoPagarB.add(interesMoraB);
			setTotalPagar(String.valueOf(totalPagar.setScale(2,BigDecimal.ROUND_UP)));
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}

	}
	

	public int traeIdAlcabala() {
		int id = 0;
		try {
			id = generalBo.ObtenerCorrelativoTabla("RA_DJALCABALA", 1);
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
			}
		return id;
	}
	
	public int traeIdAlcabalaHistorico() {
		int id = 0;
		try {
			id = generalBo.ObtenerCorrelativoTabla("ra_djalcabala_historico", 1);
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
			}
		return id;
	}


	
	private String djAlcabala;

	// invocar servicio que trae el tipo de cambio
	private BigDecimal cambioDolares = new BigDecimal(0);
	public BigDecimal getCambioDolares() {
		return cambioDolares;
	}

	public void setCambioDolares(BigDecimal cambioDolares) {
		this.cambioDolares = cambioDolares;
	}

	private Integer departamentoId = Constante.DEPARTAMENTO_ID_DEFECTO;
	private Integer provinciaId=Constante.PROVINCIA_ID_DEFECTO;
	private Integer distritoId=Constante.DISTRITO_ID_DEFECTO;
	
	public void guardar() {

		try {
			fechaDeclaracion = DateUtil.getCurrentDate();

			// GUARDANDO RDJALCABALA
			guardarDJAlcabala(obtenerDJAlcabala());

			// GUARDANDO ANEXOS
			guardarAnexos(getLstAnexosMuestra());

			// GUARDANDO DIRECCION
//			if (getDireccionAlcabalaManaged().getRaDireccionAlcabala() != null) {
//			RaDireccionAlcabala raDireccionAlcabala = getDireccionAlcabalaManaged().getRaDireccionAlcabala();
//				guardarDireccion(raDireccionAlcabala);
//			}
//
//			// GUARDANDO TRANSFERENTES
//			if(getAgregarTransferenteAlcabalaManaged().getLstTransferentes().size()>0){
//				guardarTransferentes(getAgregarTransferenteAlcabalaManaged().getLstTransferentes());				
//			}
//						
			guardarDeterminacion();
	
			flagGuardado=Boolean.TRUE;
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}

	}

	public void guardarDeterminacion()throws Exception{
		try {
			
			DtDeterminacion dtDetermina= new DtDeterminacion();
					
			if(getDtDeterminacion()!=null && getDtDeterminacion().size()>0){
				DtDeterminacion dtDeterminaAnte= new DtDeterminacion();
				dtDeterminaAnte=getDtDeterminacion().get(0);

			 dtDetermina.setBaseImponibleAnterior(dtDeterminaAnte.getBaseImponible());
			 dtDetermina.setBaseExoneradaAnterior(dtDeterminaAnte.getBaseExonerada());
			 dtDetermina.setBaseAfectaAnterior(dtDeterminaAnte.getBaseAfectaAnterior());
			 dtDetermina.setFechaActualizacion(DateUtil.getCurrentDate());
				 
			 }
				 int idDeterminacion;
					
			 idDeterminacion = generalBo.ObtenerCorrelativoTabla("dt_determinacion", 1);
			 dtDetermina.setDeterminacionId(idDeterminacion);
			 
			 dtDetermina.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
			 dtDetermina.setAnnoDeterminacion(DateUtil.getAnioActual());
			 dtDetermina.setConceptoId(Constante.CONCEPTO_ALCABALA);
			 dtDetermina.setSubconceptoId(Constante.SUBCONCEPTO_ALCABALA);
			 if(getBaseImponible()!=null&&getBaseImponible().length()>0){
			 dtDetermina.setBaseImponible(new BigDecimal(getBaseImponible()));
			 }else {
				 dtDetermina.setBaseImponible(BigDecimal.ZERO);
			}
			 if(getBaseExonerada()!=null&&getBaseExonerada().length()>0){
				 dtDetermina.setBaseExonerada(new BigDecimal(getBaseExonerada()));
			 }else {
				 dtDetermina.setBaseExonerada(BigDecimal.ZERO);
			}

			 if(getBaseAfecta()!=null&&getBaseAfecta().length()>0){
				 dtDetermina.setBaseAfecta(new BigDecimal(getBaseAfecta()));
			 }else {
				 dtDetermina.setBaseAfecta(BigDecimal.ZERO);
			}

			 if(getImpuestoPagar()!=null&&getImpuestoPagar().length()>0){
				 dtDetermina.setImpuesto(new BigDecimal(getImpuestoPagar()));
			 }else {
				 dtDetermina.setImpuesto(BigDecimal.ZERO);
			}

			 dtDetermina.setNroCuotas(1);
			 dtDetermina.setPorcPropiedad(new BigDecimal(getPorcPropiedad()));
			 dtDetermina.setDjreferenciaId(Integer.parseInt(getDjPredio()));
			 dtDetermina.setEstado(Constante.ESTADO_ACTIVO);
			 
			 alcabalaBo.guardarDeterminacionALcabala(dtDetermina);
			 guardarDeuda(dtDetermina);

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);			
			}
	}
	

	
	public void guardarDeuda(DtDeterminacion dtDetermina)throws Exception{
		try {
			CdDeuda deuda= new CdDeuda();
			int deudaId = generalBo.ObtenerCorrelativoTabla("cd_deuda", 1);
			deuda.setDeudaId(deudaId);
			deuda.setTipoDeudaId(Constante.TIPO_DEUDA_AUTOGENERADO);
			deuda.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
			deuda.setConceptoId(Constante.CONCEPTO_ALCABALA);
			deuda.setSubconceptoId(Constante.SUBCONCEPTO_ALCABALA);
			deuda.setDeterminacionId(dtDetermina.getDeterminacionId());
			deuda.setAnnoDeuda(DateUtil.getAnioActual());
			deuda.setFechaEmision(DateUtil.getCurrentDate());
			if(fechaVenceD!=null){
			deuda.setFechaVencimiento(DateUtil.dateToSqlTimestamp(fechaVenceD));
			}else{
			deuda.setFechaVencimiento(null);	
			}
			
			deuda.setNroCuota(1);
			
			if(getImpuestoPagar()!=null&&getImpuestoPagar().length()>0){
				deuda.setMontoOriginal(new BigDecimal(getImpuestoPagar()));
				}else{
				deuda.setMontoOriginal(BigDecimal.ZERO);	
			}
			if(getTotalPagar()!=null&&getTotalPagar().length()>0){
				deuda.setInsoluto(new BigDecimal(getImpuestoPagar()));
				}else{
				deuda.setInsoluto(BigDecimal.ZERO);	
			}
			
			deuda.setReajuste(BigDecimal.ZERO);
			deuda.setDerechoEmision(BigDecimal.ZERO);
			deuda.setInteresMensual(BigDecimal.ZERO);
			deuda.setInteresAnual(BigDecimal.ZERO);
			deuda.setInteresCapitalizado(BigDecimal.ZERO);
			if(getTotalPagar()!=null&&getTotalPagar().length()>0){
				deuda.setTotalDeuda(new BigDecimal(getTotalPagar()));
				}else{
				deuda.setTotalDeuda(BigDecimal.ZERO);	
			}
			
			deuda.setInsolutoCancelado(BigDecimal.ZERO);
			deuda.setReajusteCancelado(BigDecimal.ZERO);
			deuda.setDerechoEmisionCancelado(BigDecimal.ZERO);
			deuda.setInteresMensualCancelado(BigDecimal.ZERO);
			deuda.setInteresCapiCancelado(BigDecimal.ZERO);
			deuda.setTotalCancelado(BigDecimal.ZERO);
			deuda.setAreaUso(new BigDecimal(getPorcPropiedad()));
			deuda.setEstadoDeudaId(Constante.ESTADO_DEUDA_DETERMINADO);

			deuda.setEstado(Constante.ESTADO_ACTIVO);
			
			alcabalaBo.guardarDeudaAlcabala(deuda);
			
			guardarDeudaHistorica(deuda);
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);			
			}
	}
	
	public void guardarDeudaHistorica(CdDeuda deuda)throws Exception{
		try {
			CdDeudaHistorica dh= new CdDeudaHistorica();
			CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
			id.setDeudaId(deuda.getDeudaId());
			id.setHistoricaId(deuda.getDeudaId());
			dh.setId(id);
			dh.setDeterminacionId(deuda.getDeterminacionId());
			dh.setPersonaId(deuda.getPersonaId());
			dh.setTipoDeuda(deuda.getTipoDeudaId());
			dh.setFechaVencimiento(deuda.getFechaVencimiento());
			dh.setInsoluto(deuda.getInsoluto());
			dh.setTotal(deuda.getTotalDeuda());
			dh.setEstado(Constante.ESTADO_ACTIVO);
			
			alcabalaBo.guardarDeudaHistoricaAlcabala(dh);
						
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
	}
	
	public void vistaPrevia()throws Exception{
		try{   
		fechaDeclaracion = DateUtil.getCurrentDate();
		
		ImprimirAlcabalaDTO impAlcaDTO= new ImprimirAlcabalaDTO();
		obtieneValorTransferencia();
		impAlcaDTO.setDjAlcabalaId(djAlcabala);
		impAlcaDTO.setUbicacionPropiedad(tipoUbicacionPropiedad1);
		
		if (selectedTipoPred == 1) {
			impAlcaDTO.setTipoPredio(Constante.TIPO_PREDIO_URBANO);
		} else if (selectedTipoPred == 2) {
			impAlcaDTO.setTipoPredio(Constante.TIPO_PREDIO_RUSTICO);
		}
		
		impAlcaDTO.setDireccionPredio(direccionPredio);
		impAlcaDTO.setDjPredial(DjPredio);		
		impAlcaDTO.setFechaTransferencia(fechaTransferencia);
		impAlcaDTO.setsFechaTransferencia(DateUtil.convertDateToString(fechaTransferencia));
		impAlcaDTO.setFechaDeclaracion(fechaDeclaracion);
		impAlcaDTO.setsFechaDeclaracion(DateUtil.convertDateToString(fechaDeclaracion));
		impAlcaDTO.setPorPropiedad(porcPropiedad);
		
		impAlcaDTO.setTipoTransferencia(cmbTipoTransferenciaValor);

		impAlcaDTO.setTipoMoneda(cmbTipoMonedaValor);
		impAlcaDTO.setValorAutovaluo(autovaluo);
		impAlcaDTO.setValorAutovaluoAjustado(autovaluoAjuste);
		impAlcaDTO.setValorTransferencia(valorTransferencia);
		impAlcaDTO.setValorUIT(valorUIT);
		impAlcaDTO.setFactorAjuste(factorAjuste);
		impAlcaDTO.setTipoCambio(tipoCambio);
		impAlcaDTO.setAjuste(ajuste);
		impAlcaDTO.setValorTransferenciaSoles(valorTransferenciaSoles);
		impAlcaDTO.setNroUitDeduc(nroUitDeduc);
		impAlcaDTO.setMayorValorComparado(mayorValorComparado);
		impAlcaDTO.setValorDeduccion(valorDeduccion);
		impAlcaDTO.setBaseImpo(baseImponible);
		impAlcaDTO.setBaseExonerada(baseExonerada);
		impAlcaDTO.setBaseAfecta(baseAfecta);
		impAlcaDTO.setTasa(tasa);
		impAlcaDTO.setImpuestoPagar(impuestoPagar);
		impAlcaDTO.setInteresMora(interesMora);
		impAlcaDTO.setTotalPagar(totalPagar);
		impAlcaDTO.setInafectoImpuesto(inafectoImpuesto);
		
		impAlcaDTO.setTipoDocContribu(getSessionManaged().getContribuyente().getTipoDocumentoIdentidad());
		impAlcaDTO.setNroDocContri(getSessionManaged().getContribuyente().getNroDocuIdentidad());
		impAlcaDTO.setDirecContri(getSessionManaged().getContribuyente().getDomicilioPersona());
		impAlcaDTO.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
		impAlcaDTO.setPredioId(predioId);
		
		impAlcaDTO.setUsuario(usuario);
		

		if(inafectoImpuesto==Boolean.TRUE){
			impAlcaDTO.setEtiquetaAfectoImpuesto("Contribuyente inafecto a Impuesto de alcabala");
		}else{
			impAlcaDTO.setEtiquetaAfectoImpuesto("Contribuyente afecto a Impuesto de alcabala");
		}
		
		impAlcaDTO.setApellidosNombres(getSessionManaged().getContribuyente().getApellidosNombres());
		getSessionMap().put("datosRaDjAlcabalaSesion", impAlcaDTO);
		
		
			
			}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		  }
		}
	
	public void actualizar(){
		try {
			setUsuario(getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			alcabalaBo.actualizaDjAlcabala(obtenerDJAlcabala());
			guardarDjAlcabalaHistorico(obtenerDJAlcabala());

			//CAMBIA ESTADO DIRECCION PREDIO ANTERIOR
			alcabalaBo.actualizaRaDireccionAlcabala(getRaDirecAnterior());
			//GUARDA NUEVA DIRECCION
//			if (getDireccionAlcabalaManaged().getRaDireccionAlcabala() != null) {
//			RaDireccionAlcabala raDireccionAlcabala = getDireccionAlcabalaManaged().getRaDireccionAlcabala();
//				guardarDireccion(raDireccionAlcabala);
//			}
			
		
				
			//CAMBIA ESTADO DETERMINACION
			if(getDtDeterminacion().size()>0){
			alcabalaBo.actualizaDtDeterminacionALcabala(getDtDeterminacion().get(0));
			//CAMBIA ESTADO DEUDA
			alcabalaBo.actualizaDeudaALcabala(getDtDeterminacion().get(0).getDeterminacionId());
			}
			
			guardarDeterminacion();
			
			flagGuardado=Boolean.TRUE;
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
		
	}
	
	public String salir(){
		FacesUtil.closeSession("calculoAlcabalaManaged");
		return sendRedirectPrincipal();
	}
	
	private boolean flagGuardado=Boolean.FALSE;
	
	public boolean isFlagGuardado() {
		return flagGuardado;
	}

	public void setFlagGuardado(boolean flagGuardado) {
		this.flagGuardado = flagGuardado;
	}

	public void guardarTransferentes(List<BuscarPersonaDTO> lstTransferentes){
		
		try {
			for(int i=0;i<lstTransferentes.size();i++){
				
				int idTransferente = generalBo.ObtenerCorrelativoTabla("ra_transferente_alcabala", 1);

				RaTransferenteAlcabala raTransferenteAlcabala = new RaTransferenteAlcabala();
				raTransferenteAlcabala.setDjalcabalaId(Integer.parseInt(djAlcabala));
				raTransferenteAlcabala.setTransferenteId(idTransferente);		
				raTransferenteAlcabala.setSubtipoPersonaId(lstTransferentes.get(i).getSubtipoPersonaId());
				raTransferenteAlcabala.setTipoPersonaId(lstTransferentes.get(i).getTipoPersonaId());
				if(lstTransferentes.get(i).getPersonaId()!=null){
				raTransferenteAlcabala.setPersonaId(lstTransferentes.get(i).getPersonaId());	
				}				
				raTransferenteAlcabala.setTipoDocIdentidadId(lstTransferentes.get(i).getTipodocumentoIdentidadId());
				raTransferenteAlcabala.setNroDocuIdentidad(lstTransferentes.get(i).getNroDocuIdentidad());
				raTransferenteAlcabala.setApePaterno(lstTransferentes.get(i).getApellidoPaterno());
				raTransferenteAlcabala.setApeMaterno(lstTransferentes.get(i).getApellidoMaterno());
				raTransferenteAlcabala.setNombres(lstTransferentes.get(i).getPrimerNombre()+" "+lstTransferentes.get(i).getSegundoNombre());
				raTransferenteAlcabala.setNombreCompleto(lstTransferentes.get(i).getNombresCompletos());
				raTransferenteAlcabala.setDireccionCompleta(lstTransferentes.get(i).getDireccionCompleta());
				raTransferenteAlcabala.setTelefono(lstTransferentes.get(i).getTelefono());
				raTransferenteAlcabala.setReferencia(lstTransferentes.get(i).getReferencia());
				raTransferenteAlcabala.setRazonSocial(lstTransferentes.get(i).getRazonSocial());
				if(lstTransferentes.get(i).getFechaConsNac()!=null){
				raTransferenteAlcabala.setFechaConsNacimiento(DateUtil.dateToSqlTimestamp(lstTransferentes.get(i).getFechaConsNac()));
				}
				if(lstTransferentes.get(i).getFechaConsNac()!=null){
				raTransferenteAlcabala.setFechaDefuncion(DateUtil.dateToSqlTimestamp(lstTransferentes.get(i).getFechaConsNac()));					
				}

				raTransferenteAlcabala.setNroPartidaDefuncion(lstTransferentes.get(i).getPartidaDefuncion());
				raTransferenteAlcabala.setEstado(Constante.ESTADO_ACTIVO);
				
				alcabalaBo.guardarTransferenteAlcabala(raTransferenteAlcabala);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}	}
	
	private int motivoActualizacion=1;
	
	public RaDjalcabala obtenerDJAlcabala(){
		
		RaDjalcabala raDjalcabala = new RaDjalcabala();


		raDjalcabala.setDjalcabalaId(Integer.parseInt(djAlcabala));
		raDjalcabala.setPredioAlcabalaId(Integer.parseInt(djAlcabala));
		raDjalcabala.setMotivoActualizacionId(motivoActualizacion);

		Calendar calen = Calendar.getInstance();
		int anioDeclara = calen.get(Calendar.YEAR);
		raDjalcabala.setAnnoDeclaracion(anioDeclara);

		int personaId = getSessionManaged().getContribuyente().getPersonaId();
		raDjalcabala.setPersonaId(personaId);

		raDjalcabala.setFechaDeclaracion(new Timestamp(fechaDeclaracion.getTime()));
		int djId = Integer.parseInt(getDjPredio());
		raDjalcabala.setDjId(djId);
		if (isFueraDistrito == true) {
			raDjalcabala.setUbicacionPredio("F");
		} else if (isDistrital == true) {
			raDjalcabala.setUbicacionPredio("D");
		}
		raDjalcabala.setDepartamentoId(departamentoId);
		raDjalcabala.setProvinciaId(provinciaId);
		raDjalcabala.setDistritoId(distritoId);
		if(condicionPropiedadId!=null){
		raDjalcabala.setCondicionPropiedadId(condicionPropiedadId);
		}
		if(porcPropiedad.length()>0){
		raDjalcabala.setPorcPropiedad(new BigDecimal(porcPropiedad));
		}
		raDjalcabala.setCodigoPostalDistrito(null);
		String tipoPredio = "";
		if (selectedTipoPred == 1) {
			tipoPredio = Constante.TIPO_PREDIO_URBANO;
		} else if (selectedTipoPred == 2) {
			tipoPredio = Constante.TIPO_PREDIO_RUSTICO;
		}
		raDjalcabala.setTipoPredio(tipoPredio);
		if(tipoTransferencia!=null){
		raDjalcabala.setTipoTransferenciaId(tipoTransferencia);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		String fechaTrans = sdf.format(fechaTransferencia);
		raDjalcabala.setFechaTransferencia(fechaTrans);

		if (primeraVEnta == true) {
			raDjalcabala.setFlagPrimeraVenta("1");
		} else if (primeraVEnta == false) {
			raDjalcabala.setFlagPrimeraVenta("2");
		}

		
		raDjalcabala.setValorTransferencia(new BigDecimal(valorTransferencia));
		raDjalcabala.setAutovaluoTotal(new BigDecimal(autovaluo));
		raDjalcabala.setAutovaluoAfecta(new BigDecimal(autovaluo));
		if(ajuste==null){
			ajuste="0";
		}
		raDjalcabala.setAjuste(new BigDecimal(ajuste));
		if(autovaluoAjuste==null||autovaluoAjuste.length()==0){
			autovaluoAjuste="0";
		}
		raDjalcabala.setAutovaluoAjustado(new BigDecimal(autovaluoAjuste));
		if(tasa==null){
			tasa="0";
		}
		raDjalcabala.setTasa(new BigDecimal(tasa));
		if(baseImponible==null){
			baseImponible="0";
		}
		raDjalcabala.setBaseImponible(new BigDecimal(baseImponible));
		if(baseExonerada==null){
			baseExonerada="0";
		}
		raDjalcabala.setBaseExonerada(new BigDecimal(baseExonerada));
		if(baseAfecta==null){
			baseAfecta="0";
		}
		raDjalcabala.setBaseAfecta(new BigDecimal(baseAfecta));
		if(impuestoPagar==null){
			impuestoPagar="0";
		}
		raDjalcabala.setValorImpuesto(new BigDecimal(impuestoPagar));
		if(interesMora==null){
			interesMora="0";
		}
		raDjalcabala.setInteres(new BigDecimal(interesMora));
		if(totalPagar==null){
			totalPagar="0";
		}
		raDjalcabala.setTotalDeuda(new BigDecimal(totalPagar));
		raDjalcabala.setGlosa("");
		raDjalcabala.setEstado(Constante.ESTADO_ACTIVO);
		raDjalcabala.setTipoDocumentoSustentoId(0);

		if (new BigDecimal(interesMora).compareTo(new BigDecimal("0")) > 0) {
			raDjalcabala.setTieneDeuda("S");
		} else {
			raDjalcabala.setTieneDeuda("N");
		}
		
		raDjalcabala.setNroDocumentoSustento("");

		raDjalcabala.setTipoMonedaId(tipoMonedaId);
		if(valorTransferenciaSoles==null){
			valorTransferenciaSoles="0";
		}
		raDjalcabala.setValorTransferenciaSoles(new BigDecimal(valorTransferenciaSoles));
		
		return raDjalcabala;
	}

	public void guardarDJAlcabala(RaDjalcabala raDjalcabala){
		try {
			alcabalaBo.guardaDjAlcabala(raDjalcabala);
			guardarDjAlcabalaHistorico(raDjalcabala);
					
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
	}
	
	public void guardarDjAlcabalaHistorico(RaDjalcabala raDjalcabala){
		try {

			RaDjalcabalaHistorico raDjalcabalaHistorico= new RaDjalcabalaHistorico();
			raDjalcabalaHistorico.setDjalcabalaHistoricoId(traeIdAlcabalaHistorico());
			raDjalcabalaHistorico.setTipoOperacionId(1);
			raDjalcabalaHistorico.setFechaOperacion(raDjalcabala.getFechaRegistro());
			raDjalcabalaHistorico.setDjalcabalaId(raDjalcabala.getDjalcabalaId());
			raDjalcabalaHistorico.setPredioAlcabalaId(raDjalcabala.getPredioAlcabalaId());
			raDjalcabalaHistorico.setMotivoActualizacionId(raDjalcabala.getMotivoActualizacionId());
			raDjalcabalaHistorico.setAnnoDeclaracion(raDjalcabala.getAnnoDeclaracion());
			raDjalcabalaHistorico.setPersonaId(raDjalcabala.getPersonaId());
			raDjalcabalaHistorico.setFechaDeclaracion(raDjalcabala.getFechaDeclaracion());
			raDjalcabalaHistorico.setDjId(raDjalcabala.getDjId());
			raDjalcabalaHistorico.setUbicacionPredio(raDjalcabala.getUbicacionPredio());
			raDjalcabalaHistorico.setDepartamentoId(raDjalcabala.getDepartamentoId());
			raDjalcabalaHistorico.setProvinciaId(raDjalcabala.getProvinciaId());
			raDjalcabalaHistorico.setDistritoId(raDjalcabala.getDistritoId());
			raDjalcabalaHistorico.setCondicionPropiedadId(raDjalcabala.getCondicionPropiedadId());
			raDjalcabalaHistorico.setPorcPropiedad(raDjalcabala.getPorcPropiedad());
			raDjalcabalaHistorico.setCodigoPostalDistrito(raDjalcabala.getCodigoPostalDistrito());
			raDjalcabalaHistorico.setTipoPredio(raDjalcabala.getTipoPredio());
			raDjalcabalaHistorico.setTipoTransferenciaId(raDjalcabala.getTipoTransferenciaId());
			raDjalcabalaHistorico.setFechaTransferencia(raDjalcabala.getFechaTransferencia());
			raDjalcabalaHistorico.setFlagPrimeraVenta(raDjalcabala.getFlagPrimeraVenta());
			raDjalcabalaHistorico.setNotariaId(raDjalcabala.getNotariaId());
			raDjalcabalaHistorico.setFormatoFormulario(raDjalcabala.getFormatoFormulario());
			raDjalcabalaHistorico.setNroOrdenDj(raDjalcabala.getNroOrdenDj());
			raDjalcabalaHistorico.setNroFormulario(raDjalcabala.getNroFormulario());
			raDjalcabalaHistorico.setValorTransferencia(raDjalcabala.getValorTransferencia());
			raDjalcabalaHistorico.setAutovaluoTotal(raDjalcabala.getAutovaluoTotal());
			raDjalcabalaHistorico.setAutovaluoAfecta(raDjalcabala.getAutovaluoAfecta());
			raDjalcabalaHistorico.setAjuste(raDjalcabala.getAjuste());
			raDjalcabalaHistorico.setAutovaluoAjustado(raDjalcabala.getAutovaluoAjustado());
			raDjalcabalaHistorico.setTasa(raDjalcabala.getTasa());
			raDjalcabalaHistorico.setBaseImponible(raDjalcabala.getBaseImponible());
			raDjalcabalaHistorico.setBaseExonerada(raDjalcabala.getBaseExonerada());
			raDjalcabalaHistorico.setBaseAfecta(raDjalcabala.getBaseAfecta());
			raDjalcabalaHistorico.setValorImpuesto(raDjalcabala.getValorImpuesto());
			raDjalcabalaHistorico.setInteres(raDjalcabala.getInteres());
			raDjalcabalaHistorico.setTotalDeuda(raDjalcabala.getTotalDeuda());
			raDjalcabalaHistorico.setGlosa(raDjalcabala.getGlosa());
			raDjalcabalaHistorico.setEstado(raDjalcabala.getEstado());
			raDjalcabalaHistorico.setTipoDocumentoSustentoId(raDjalcabala.getTipoDocumentoSustentoId());
			raDjalcabalaHistorico.setNroDocumentoSustento(raDjalcabala.getNroDocumentoSustento());
			raDjalcabalaHistorico.setTieneDeuda(raDjalcabala.getTieneDeuda());
			raDjalcabalaHistorico.setTipoMonedaId(raDjalcabala.getTipoMonedaId());
			raDjalcabalaHistorico.setValorTransferenciaSoles(raDjalcabala.getValorTransferenciaSoles());
			raDjalcabalaHistorico.setTasaAjuste(raDjalcabala.getTasaAjuste());
			
			alcabalaBo.guardaDjAlcabalaHistorico(raDjalcabalaHistorico);
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
	}
	
	
	public void guardarDireccion(RaDireccionAlcabala raDireccionAlcabala){
		try {
			int direccionAlcabalaId = generalBo.ObtenerCorrelativoTabla("ra_direccion_alcabala",1);
			raDireccionAlcabala.setDireccionAlcabalaId(direccionAlcabalaId);
			alcabalaBo.guardarDireccionAlcabala(raDireccionAlcabala);
			RaDireccionAlcabalaHistorico raDireccionAlcabalaHistorico= new RaDireccionAlcabalaHistorico();
			raDireccionAlcabalaHistorico.setDireccionAlcabalaHistoricoId(traeIdDireccionAlcabalaHistorico());
			raDireccionAlcabalaHistorico.setTipoOperacionId(0);
			raDireccionAlcabalaHistorico.setFechaOperacion(raDireccionAlcabala.getFechaRegistro());
			raDireccionAlcabalaHistorico.setDjalcabalaId(raDireccionAlcabala.getDjalcabalaId());
			raDireccionAlcabalaHistorico.setDireccionId(raDireccionAlcabala.getDireccionAlcabalaId());
			raDireccionAlcabalaHistorico.setTipoDireccion(raDireccionAlcabala.getTipoDireccion());
			raDireccionAlcabalaHistorico.setDptoId(raDireccionAlcabala.getDptoId());
			raDireccionAlcabalaHistorico.setProvinciaId(raDireccionAlcabala.getProvinciaId());
			raDireccionAlcabalaHistorico.setDistritoId(raDireccionAlcabala.getDistritoId());
			raDireccionAlcabalaHistorico.setTipoViaId(raDireccionAlcabala.getTipoViaId());
			raDireccionAlcabalaHistorico.setViaId(raDireccionAlcabala.getViaId());
			raDireccionAlcabalaHistorico.setDescVia(raDireccionAlcabala.getDescVia());

			raDireccionAlcabalaHistorico.setNumero(raDireccionAlcabala.getNumero());
			raDireccionAlcabalaHistorico.setLetra(raDireccionAlcabala.getLetra());
			raDireccionAlcabalaHistorico.setNumero2(raDireccionAlcabala.getNumero2());
			raDireccionAlcabalaHistorico.setLetra2(raDireccionAlcabala.getLetra2());
			raDireccionAlcabalaHistorico.setTipoEdificacionId(raDireccionAlcabala.getTipoEdificacionId());
			raDireccionAlcabalaHistorico.setNombreEdificacion(raDireccionAlcabala.getNombreEdificacion());
			raDireccionAlcabalaHistorico.setPiso(raDireccionAlcabala.getPiso());
			raDireccionAlcabalaHistorico.setTipoInteriorId(raDireccionAlcabala.getTipoInteriorId());
			raDireccionAlcabalaHistorico.setNombreInterior(raDireccionAlcabala.getNombreInterior());
			raDireccionAlcabalaHistorico.setNumeroInterior(raDireccionAlcabala.getNumeroInterior());
			raDireccionAlcabalaHistorico.setLetraInterior(raDireccionAlcabala.getLetraInterior());
			raDireccionAlcabalaHistorico.setTipoIngresoId(raDireccionAlcabala.getTipoIngresoId());
			raDireccionAlcabalaHistorico.setNombreIngreso(raDireccionAlcabala.getNombreIngreso());
			raDireccionAlcabalaHistorico.setTipoAgrupamientoId(raDireccionAlcabala.getTipoAgrupamientoId());
			raDireccionAlcabalaHistorico.setNombreAgrupamiento(raDireccionAlcabala.getNombreAgrupamiento());
			raDireccionAlcabalaHistorico.setManzana(raDireccionAlcabala.getManzana());
			raDireccionAlcabalaHistorico.setLote(raDireccionAlcabala.getLote());
			raDireccionAlcabalaHistorico.setManzanaCatastral(raDireccionAlcabala.getManzanaCatastral());
			raDireccionAlcabalaHistorico.setReferencia(raDireccionAlcabala.getReferencia());
			raDireccionAlcabalaHistorico.setDireccionCompleta(raDireccionAlcabala.getDireccionCompleta());
			raDireccionAlcabalaHistorico.setLugar(raDireccionAlcabala.getLugar());
			raDireccionAlcabalaHistorico.setNombrePredio(raDireccionAlcabala.getNombrePredio());
			raDireccionAlcabalaHistorico.setPropietarioPredioNorte(raDireccionAlcabala.getPropietarioPredioNorte());
			raDireccionAlcabalaHistorico.setPredioColindanteNorte(raDireccionAlcabala.getPredioColindanteNorte());
			raDireccionAlcabalaHistorico.setPredioColindanteSur(raDireccionAlcabala.getPredioColindanteSur());
			raDireccionAlcabalaHistorico.setSectorId(raDireccionAlcabala.getSectorId());
			raDireccionAlcabalaHistorico.setPredioColindanteSur(raDireccionAlcabala.getPredioColindanteSur());
			raDireccionAlcabalaHistorico.setPropietarioPredioEste(raDireccionAlcabala.getPropietarioPredioEste());
			raDireccionAlcabalaHistorico.setPredioColindanteEste(raDireccionAlcabala.getPredioColindanteEste());
			raDireccionAlcabalaHistorico.setPropietarioPredioOeste(raDireccionAlcabala.getPropietarioPredioOeste());
			raDireccionAlcabalaHistorico.setPredioColindanteOeste(raDireccionAlcabala.getPredioColindanteOeste());
			raDireccionAlcabalaHistorico.setDomicilioRustico(raDireccionAlcabala.getDomicilioRustico());
			raDireccionAlcabalaHistorico.setEstadoId(raDireccionAlcabala.getEstadoId());
			
			alcabalaBo.guardarDireccionAlcabalaHstorico(raDireccionAlcabalaHistorico);
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
		
	}
	
	private FileUpload archivo;
	
	public FileUpload getArchivo() {
		return archivo;
	}

	public void setArchivo(FileUpload archivo) {
		this.archivo = archivo;
	}
	
	private List<AnexosDeclaVehicDTO> lstAnexosMuestra = new ArrayList<AnexosDeclaVehicDTO>();

	public void guardarAnexos( List<AnexosDeclaVehicDTO> lstAnexosMuestra){
		try {
			if (lstAnexosMuestra.size()>0) {
				int djAlcabalaSustento = generalBo.ObtenerCorrelativoTabla("ra_alcabala_sustento",1);

				for (int i = 0; i < lstAnexosMuestra.size(); i++) {
				
				RaAlcabalaSustento raAlcabalaSustento = new RaAlcabalaSustento();
				raAlcabalaSustento.setRaAlcabalaSustentoId(djAlcabalaSustento);
				raAlcabalaSustento.setDjalcabalaId(Integer.parseInt(djAlcabala));
				raAlcabalaSustento.setDocSustentatorioId(lstAnexosMuestra.get(i).getDocSustentatorioId());
				raAlcabalaSustento.setNumeroDocumento(lstAnexosMuestra.get(i).getGlosaDoc());
				raAlcabalaSustento.setReferencia(lstAnexosMuestra.get(i).getNomDocAdjunto());
				raAlcabalaSustento.setContentId(lstAnexosMuestra.get(i).getContentId());
				raAlcabalaSustento.setEstado(Constante.ESTADO_ACTIVO);
				
				alcabalaBo.guardarDocAnexos(raAlcabalaSustento);
				 
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
			}
		
	}
	
	public int traeIdDireccionAlcabalaHistorico() {

		try {

			int id = generalBo.ObtenerCorrelativoTabla("ra_direccion_alcabala_historico",1);

			return id;
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);

			return 0;
		}
	}
	

	public int traeIdAlcabalaSustento() {

		try {

			int id = generalBo.ObtenerCorrelativoTabla("ra_alcabala_sustento", 1);

			return id;
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
			return 0;
		}
	}

	
//	public DireccionAlcabalaManaged getDireccionAlcabalaManaged() {
//		return (DireccionAlcabalaManaged) getManaged("direccionAlcabalaManaged");
//	}



	public List<SelectItem> getLstTipoMoneda() {
		return lstTipoMoneda;
	}

	public void setLstTipoMoneda(List<SelectItem> lstTipoMoneda) {
		this.lstTipoMoneda = lstTipoMoneda;
	}

	public HtmlComboBox getCmbTipoMoneda() {
		return cmbTipoMoneda;
	}

	public void setCmbTipoMoneda(HtmlComboBox cmbTipoMoneda) {
		this.cmbTipoMoneda = cmbTipoMoneda;
	}

	public String getMayorValorComparado() {
		return mayorValorComparado;
	}

	public void setMayorValorComparado(String mayorValorComparado) {
		this.mayorValorComparado = mayorValorComparado;
	}

	public String getValorDeduccion() {
		return valorDeduccion;
	}

	public void setValorDeduccion(String valorDeduccion) {
		this.valorDeduccion = valorDeduccion;
	}

	public String getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(String baseImponible) {
		this.baseImponible = baseImponible;
	}

	public String getBaseExonerada() {
		return baseExonerada;
	}

	public void setBaseExonerada(String baseExonerada) {
		this.baseExonerada = baseExonerada;
	}

	public Date getFechaTransferencia() {
		return fechaTransferencia;
	}

	public void setFechaTransferencia(Date fechaTransferencia) {
		this.fechaTransferencia = fechaTransferencia;
	}

	public boolean isPrimeraVEnta() {
		return primeraVEnta;
	}

	public void setPrimeraVEnta(boolean primeraVEnta) {
		this.primeraVEnta = primeraVEnta;
	}

	public List<SelectItem> getLstCondicionPropiedad() {
		return lstCondicionPropiedad;
	}

	public void setLstCondicionPropiedad(List<SelectItem> lstCondicionPropiedad) {
		this.lstCondicionPropiedad = lstCondicionPropiedad;
	}

	public List<SelectItem> getLstsubtipopersona() {
		return lstsubtipopersona;
	}

	public void setLstsubtipopersona(List<SelectItem> lstsubtipopersona) {
		this.lstsubtipopersona = lstsubtipopersona;
	}

	public List<SelectItem> getlstTipoPersona() {
		return lstTipoPersona;
	}

	public void setlstTipoPersona(List<SelectItem> lstTipoPersona) {
		this.lstTipoPersona = lstTipoPersona;
	}

	public boolean isUbicacionPredio() {
		return ubicacionPredio;
	}

	public void setUbicacionPredio(boolean ubicacionPredio) {
		this.ubicacionPredio = ubicacionPredio;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getNro1() {
		return nro1;
	}

	public void setNro1(String nro1) {
		this.nro1 = nro1;
	}

	public String getLetra1() {
		return letra1;
	}

	public void setLetra1(String letra1) {
		this.letra1 = letra1;
	}

	public String getPorcPropiedad() {
		return porcPropiedad;
	}

	public void setPorcPropiedad(String porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}

	public int getSelectedTipoPred() {
		return selectedTipoPred;
	}

	public void setSelectedTipoPred(int selectedTipoPred) {
		this.selectedTipoPred = selectedTipoPred;
	}

	public String getDjAlcabala() {
		return djAlcabala;
	}

	public void setDjAlcabala(String djAlcabala) {
		this.djAlcabala = djAlcabala;
	}

	public List<RaAlcabalaSustento> getLstAnexos() {
	return lstAnexos;
	}
	
	public void setLstAnexos(List<RaAlcabalaSustento> lstAnexos) {
	this.lstAnexos = lstAnexos;
	}

	public String getDireccionPredio() {
		return direccionPredio;
	}

	public void setDireccionPredio(String direccionPredio) {
		this.direccionPredio = direccionPredio;
	}

	public String getTipoUbicacionPropiedad1() {
		return tipoUbicacionPropiedad1;
	}

	public void setTipoUbicacionPropiedad1(String tipoUbicacionPropiedad1) {
		this.tipoUbicacionPropiedad1 = tipoUbicacionPropiedad1;
	}



	public HtmlComboBox getCmbUbicacion() {
		return cmbUbicacion;
	}

	public void setCmbUbicacion(HtmlComboBox cmbUbicacion) {
		this.cmbUbicacion = cmbUbicacion;
	}

	public boolean isFueraDistrito() {
		return isFueraDistrito;
	}

	public void setFueraDistrito(boolean isFueraDistrito) {
		this.isFueraDistrito = isFueraDistrito;
	}

	public boolean isDistrital() {
		return isDistrital;
	}

	public void setDistrital(boolean isDistrital) {
		this.isDistrital = isDistrital;
	}

	public String getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getiPMmesAnte() {
		return iPMmesAnte;
	}

	public void setiPMmesAnte(String iPMmesAnte) {
		this.iPMmesAnte = iPMmesAnte;
	}

	public String getiPMdicAnte() {
		return iPMdicAnte;
	}

	public void setiPMdicAnte(String iPMdicAnte) {
		this.iPMdicAnte = iPMdicAnte;
	}

	public String getFactorAjuste() {
		return factorAjuste;
	}

	public void setFactorAjuste(String factorAjuste) {
		this.factorAjuste = factorAjuste;
	}

	public List<AnexosDeclaVehicDTO> getLstAnexosMuestra() {
		return lstAnexosMuestra;
	}

	public void setLstAnexosMuestra(List<AnexosDeclaVehicDTO> lstAnexosMuestra) {
		this.lstAnexosMuestra = lstAnexosMuestra;
	}

	public String getTextoIPM2() {
		return textoIPM2;
	}

	public void setTextoIPM2(String textoIPM2) {
		this.textoIPM2 = textoIPM2;
	}

	public String getTextoIPM1() {
		return textoIPM1;
	}

	public void setTextoIPM1(String textoIPM1) {
		this.textoIPM1 = textoIPM1;
	}

	public String getCmbTipoTransferenciaValor() {
		return cmbTipoTransferenciaValor;
	}

	public void setCmbTipoTransferenciaValor(String cmbTipoTransferenciaValor) {
		this.cmbTipoTransferenciaValor = cmbTipoTransferenciaValor;
	}

	public String getCmbTipoMonedaValor() {
		return cmbTipoMonedaValor;
	}

	public void setCmbTipoMonedaValor(String cmbTipoMonedaValor) {
		this.cmbTipoMonedaValor = cmbTipoMonedaValor;
	}

	public Boolean getBotonGuarda() {
		return botonGuarda;
	}

	public void setBotonGuarda(Boolean botonGuarda) {
		this.botonGuarda = botonGuarda;
	}

	public Boolean getCalculado() {
		return calculado;
	}

	public void setCalculado(Boolean calculado) {
		this.calculado = calculado;
	}

	public ArrayList<BuscarPersonaDTO> getLstTransferentesAnterior() {
		return lstTransferentesAnterior;
	}

	public void setLstTransferentesAnterior(ArrayList<BuscarPersonaDTO> lstTransferentesAnterior) {
		this.lstTransferentesAnterior = lstTransferentesAnterior;
	}

	public Boolean getBotonActualiza() {
		return botonActualiza;
	}

	public void setBotonActualiza(Boolean botonActualiza) {
		this.botonActualiza = botonActualiza;
	}

	public Boolean getInafectoImpuesto() {
		return inafectoImpuesto;
	}

	public void setInafectoImpuesto(Boolean inafectoImpuesto) {
		this.inafectoImpuesto = inafectoImpuesto;
	}

	public Integer getPredioId() {
		return predioId;
	}

	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}

	public ArrayList<DtDeterminacion> getDtDeterminacion() {
		return dtDeterminacion;
	}

	public void setDtDeterminacion(ArrayList<DtDeterminacion> dtDeterminacion) {
		this.dtDeterminacion = dtDeterminacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFechaDeclaracion() {
		return fechaDeclaracion;
	}

	public void setFechaDeclaracion(Date fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getCmbPredioId() {
		return cmbPredioId;
	}

	public void setCmbPredioId(Integer cmbPredioId) {
		this.cmbPredioId = cmbPredioId;
	}

	public List<SelectItem> getLstSelectItemPredios() {
		return lstSelectItemPredios;
	}

	public void setLstSelectItemPredios(List<SelectItem> lstSelectItemPredios) {
		this.lstSelectItemPredios = lstSelectItemPredios;
	}

	public ArrayList<FindRpDjPredial> getLstPredios() {
		return lstPredios;
	}

	public void setLstPredios(ArrayList<FindRpDjPredial> lstPredios) {
		this.lstPredios = lstPredios;
	}

	public List<Integer> getLstPrediosId() {
		return lstPrediosId;
	}

	public void setLstPrediosId(List<Integer> lstPrediosId) {
		this.lstPrediosId = lstPrediosId;
	}

	public String getNroFormulario() {
		return nroFormulario;
	}

	public void setNroFormulario(String nroFormulario) {
		this.nroFormulario = nroFormulario;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}


}
