package com.sat.sisat.predial.managed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.alcabala.managed.AgregarTransferenteAlcabalaManaged;
import com.sat.sisat.alcabala.managed.CalculoAlcabalaManaged;
import com.sat.sisat.alcabala.managed.DireccionAlcabalaManaged;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.GnLugar;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.RaDireccionAlcabala;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;


@ManagedBean
@ViewScoped
public class BuscarPredioInscripcionManaged extends BaseManaged {
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	//INICIO ITANTAMANGO
	private int desactiva;
	
	public int getDesactiva() {
		return desactiva;
	}

	public void setDesactiva(int desactiva) {
		this.desactiva = desactiva;
	}
	//FIN ITANTAMANGO

	private String apellidosNombres;
	private String razonSocial;
	private String tipoDocumento;
	private String numeroDocumento;
	
	private String codigoPredio;
	private Integer personaId;
	
	private String personaIdAnt;
	private String codigoPredioAnt;
	
	private String sector;
	private String lugar;//--
	
	private String tipoVia;//--
	private String via;//--
	
	private String direccion;
	
	private Integer numeroCuadra;
	private Integer lado;
	private Integer numeroManzana;
	private Integer sectorLugarId;
	private String numeroVia;
	
	private String valueComboBoxLado = "";
	private List<SelectItem> listLadoCuadra = new ArrayList<SelectItem>();
	
	private int scrollerPage;
	
	private FindRpDjPredial findRpDjPredial=new FindRpDjPredial();
	//--ini
	private ArrayList<FindRpDjPredial> records;
	private FindRpDjPredial currentItem = new FindRpDjPredial();
	//--fin
	
	private List<SelectItem> lstsector=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnSector=new HashMap<String, Integer>();
	
	private List<SelectItem> lstTipDocIdentidad = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapTipDocIdentidad = new HashMap<String, Integer>();
	
	private List<SelectItem> lstlugar=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnLugar=new HashMap<String, Integer>();
	
	private List<SelectItem> lsttipovia=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnTipoVia=new HashMap<String, Integer>();
	
	private List<SelectItem> lstvia=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnVia=new HashMap<String, Integer>();
	
	public void limpiar(){
		setApellidosNombres("");
		setRazonSocial("");
		setTipoDocumento("");
		setNumeroDocumento("");
		setCodigoPredio("");
		setSector("");
		setLugar("");//--
		setTipoVia("");//--
		setVia("");//--
		setDireccion("");
		setPersonaId(null);
		setPersonaIdAnt(null);
		setCodigoPredioAnt("");
		setNumeroCuadra(null);
		setLado(null);
		setNumeroManzana(null);
		setNumeroVia("");
		setValueComboBoxLado("");
		
		records=new ArrayList<FindRpDjPredial>();
	}
	
	//INICIO ITantamango
	public void seleccionaDatos(){
		try{
			if(currentItem!=null){
				setDireccion(String.valueOf(currentItem.getDireccionCompleta()));
				
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public AgregarTransferenteAlcabalaManaged getAgregarTransferenteAlcabalaManaged(){
		return (AgregarTransferenteAlcabalaManaged) getManaged("agregarTransferenteAlcabalaManaged");
	}
	
	public DireccionAlcabalaManaged getDireccionAlcabalaManaged(){
		return (DireccionAlcabalaManaged) getManaged("direccionAlcabalaManaged");
	}
	
	public void changeListenerComboBoxLado(ValueChangeEvent event) {
		String key = (String) event.getNewValue();

		if (key.contains("2 - IZQ")) {
			this.setLado(2);
			this.valueComboBoxLado = "2 - IZQ";
		} else if (key.contains("1 - DER")) {
			this.setLado(1);
			this.valueComboBoxLado = "1 - DER";
		} else {
			WebMessages.messageError("El lado es incorrecto");
			this.valueComboBoxLado = null;
		}

	}
	
	public void enviarPredio(){
		try{
			if(currentItem!=null){
			
				CalculoAlcabalaManaged calculo=(CalculoAlcabalaManaged) getManaged("calculoAlcabalaManaged");
				
				calculo.setProperty(currentItem);

				String djAlcabala = calculo.getDjAlcabala();
				int personaId=currentItem.getPersonaId();
				int tipoDocIdentidadId= currentItem.getTipoDocIdentidad();
				String nombreCompleto=currentItem.getApellidosNombres();
				String tipoDocIden= currentItem.getDescripcionDocIdentidad();
				String nroDocIden= currentItem.getNumeroDocIdentidad();
				int tipoPersonaId= currentItem.getTipoPersonaId();
					
				//AGREGANDO TRANSFERENTES
				BuscarPersonaDTO buscarPersonaDTO= new BuscarPersonaDTO();
				buscarPersonaDTO.setApellidosNombres(nombreCompleto);
				buscarPersonaDTO.setTipoDocIdentidad(tipoDocIden);
				buscarPersonaDTO.setNroDocuIdentidad(nroDocIden);
				buscarPersonaDTO.setDireccionCompleta(direccion);
				buscarPersonaDTO.setTipoPersonaId(tipoPersonaId);
				buscarPersonaDTO.setSubtipoPersonaId(currentItem.getSubtipoPersonaId());
				buscarPersonaDTO.setPersonaId(personaId);
				buscarPersonaDTO.setTipodocumentoIdentidadId(tipoDocIdentidadId);
				buscarPersonaDTO.setNroDocuIdentidad(nroDocIden);
				buscarPersonaDTO.setApellidoPaterno(currentItem.getApePaterno());
				buscarPersonaDTO.setApellidoMaterno(currentItem.getApeMaterno());
				buscarPersonaDTO.setRazonSocial(currentItem.getRazonSocial());
				buscarPersonaDTO.setPrimerNombre(null);
				buscarPersonaDTO.setSegundoNombre(null);
				buscarPersonaDTO.setNombresCompletos(nombreCompleto);
				buscarPersonaDTO.setReferencia(null);
				buscarPersonaDTO.setTelefono(currentItem.getTelefonoPersona());
				List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();
				lstTransferentes.add(buscarPersonaDTO);
				getAgregarTransferenteAlcabalaManaged().setLstTransferentes(lstTransferentes);
				
				//AGREGANDO DIRECCION ALCABALA
				RaDireccionAlcabala raDirecAlcabala = new RaDireccionAlcabala();
				raDirecAlcabala= getDireccionAlcabalaManaged().getRaDireccionAlcabala();
				raDirecAlcabala.setDjalcabalaId(Integer.parseInt(djAlcabala));
				raDirecAlcabala.setDptoId(Constante.DEPARTAMENTO_ID_DEFECTO);
				raDirecAlcabala.setProvinciaId(Constante.PROVINCIA_ID_DEFECTO);
				//raDirecAlcabala.setDistritoId(currentItem.getDistritoId());
				raDirecAlcabala.setDistritoId(Constante.DISTRITO_ID_DEFECTO);
				raDirecAlcabala.setTipoViaId(currentItem.getTipoViaId());
				raDirecAlcabala.setViaId(currentItem.getViaId());
				raDirecAlcabala.setDescVia(currentItem.getViadescripcion());
				raDirecAlcabala.setManzana(currentItem.getManzana());
				raDirecAlcabala.setDireccionCompleta(currentItem.getDireccionCompleta());
				raDirecAlcabala.setEstadoId(Constante.ESTADO_ACTIVO);
				raDirecAlcabala.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
				raDirecAlcabala.setFechaRegistro(DateUtil.getCurrentDate());
				raDirecAlcabala.setTerminal(getSessionManaged().getTerminalLogIn());
				
			}else {
				
				WebMessages.messageInfo("Debe iSeleccionar un Predio");
			}
		}catch(Exception e){
			WebMessages.messageInfo("Debe iSeleccionar un Predio");;
		}
	}
	
	//FIN ITantamango
	
	public BuscarPredioInscripcionManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			List<MpTipoDocuIdentidad> lstMpTipoDocuIdentidad=registroPrediosBo.getAllMpTipoDocuIdentidad();
			Iterator<MpTipoDocuIdentidad> it1 = lstMpTipoDocuIdentidad.iterator();
			lstTipDocIdentidad=new ArrayList<SelectItem>();
			 
	        while (it1.hasNext()){
	        	MpTipoDocuIdentidad obj = it1.next();  
	        	lstTipDocIdentidad.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoDocIdentidadId())));  
	        	mapTipDocIdentidad.put(obj.getDescripcion().trim(), obj.getTipoDocIdentidadId());
	        }
	        
	        List<GnSector> lstGnSector=registroPrediosBo.getAllGnSector();
			Iterator<GnSector> it2 = lstGnSector.iterator();  
			lstsector = new ArrayList<SelectItem>();
			while (it2.hasNext()){
	        	GnSector obj = it2.next();  
	        	lstsector.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getSectorId())));
	        	mapGnSector.put(obj.getDescripcion(), obj.getSectorId());
	        }//setLugar("");//--
			
	        List<GnTipoVia> lstGnTipoVia=registroPrediosBo.getAllGnTipoVia();
			Iterator<GnTipoVia> it = lstGnTipoVia.iterator();  
			lsttipovia = new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	GnTipoVia obj = it.next();  
	        	lsttipovia.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoViaId())));
	        	mapGnTipoVia.put(obj.getDescripcion(), obj.getTipoViaId());
	        }//setVia("");//--
			
	        listLadoCuadra.add(new SelectItem("1 - DER", "1"));
			listLadoCuadra.add(new SelectItem("2 - IZQ", "2"));
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void buscar(){
		try{
			records=new ArrayList<FindRpDjPredial>();
			
			//getRpDjpredial2(apellidosNombres, razonSocial, tipoDocIdentidad, numeroDocIdentidad, 
			//codigoPredio, tipoViaId, viaId, sectorId, lugarId, 
			//direccion, djId, personaId, 
			//codAntSatcaj, codigoAnterior, 
			//numeroCuadra, lado, numeroManzana, numeroVia, esPropietario)
			
			if(getCodigoPredio()!=null&&getCodigoPredio().trim().length()>0){				
				records=registroPrediosBo.getRpDjpredial2(null,null,null,null,getCodigoPredio(),null,null,null,null,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if(getPersonaId()!=null&&getPersonaId().intValue()>0){
				records=registroPrediosBo.getRpDjpredial2(null,null,null,null,null,null,null,null,null,null,null,getPersonaId(),null,null,null,null,null,null,Boolean.FALSE);
			}else if(getApellidosNombres()!=null&&getApellidosNombres().trim().length()>0){
				records=registroPrediosBo.getRpDjpredial2(getApellidosNombres(),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if(getRazonSocial()!=null&&getRazonSocial().trim().length()>0){
				records=registroPrediosBo.getRpDjpredial2(null,getRazonSocial(),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if(getTipoDocumento()!=null&&getTipoDocumento().trim().length()>0&&getNumeroDocumento()!=null&&getNumeroDocumento().trim().length()>0){
				Integer tipoDocIdentidadId=mapTipDocIdentidad.get(getTipoDocumento().toString());
				records=registroPrediosBo.getRpDjpredial2(null,null,tipoDocIdentidadId,getNumeroDocumento(),null,null,null,null,null,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if(getTipoVia()!=null&&getTipoVia().trim().length()>0
					&&getVia()!=null&&getVia().trim().length()>0){
				
				Integer tipoViaId=mapGnTipoVia.get(getTipoVia().trim());
				Integer viaId=mapGnVia.get(getVia().trim());
				
				records=registroPrediosBo.getRpDjpredial2(null,null,null,null,null,tipoViaId,viaId,null,null,null,null,null,null,null,numeroCuadra, lado, numeroManzana,numeroVia,Boolean.FALSE);
				
			}else if(getLugar()!=null&&getLugar().trim().length()>0
					&&getSector()!=null&&getSector().trim().length()>0){
				Integer sectorId=mapGnSector.get(getSector().trim());
				Integer lugarId=mapGnLugar.get(getLugar().trim());
				records=registroPrediosBo.getRpDjpredial2(null,null,null,null,null,null,null,sectorId,lugarId,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
				
				if(getTipoVia()!=null&&getTipoVia().trim().length()>0
						&&getVia()!=null&&getVia().trim().length()>0){
					
					Integer tipoViaId=mapGnTipoVia.get(getTipoVia().trim());
					Integer viaId=mapGnVia.get(getVia().trim());
					
					records=registroPrediosBo.getRpDjpredial2(null,null,null,null,null,tipoViaId,viaId,sectorId,lugarId,null,null,null,null,null,numeroCuadra,lado,numeroManzana,numeroVia,Boolean.FALSE);
					
				}
			}else if(getDireccion()!=null&&getDireccion().trim().length()>0){
				records=registroPrediosBo.getRpDjpredial2(null,null,null,null,null,null,null,null,null,getDireccion(),null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if (getPersonaIdAnt()!=null&&!getPersonaIdAnt().isEmpty()){
				records=registroPrediosBo.getRpDjpredial2(null,null,null,null,null,null,null,null,null,null,null,null,getPersonaIdAnt(),null,null,null,null,null,Boolean.FALSE);
			}else if(getCodigoPredioAnt()!=null&&getCodigoPredioAnt().trim().length()>0){
				records=registroPrediosBo.getRpDjpredial2(null,null,null,null,null,null,null,null,null,null,null,null,null,getCodigoPredioAnt(),null,null,null,null,Boolean.FALSE);
			}else if(getNumeroManzana()!=null&&getNumeroManzana().intValue()>0){
				records=registroPrediosBo.getRpDjpredial2(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null, null,numeroManzana,null,Boolean.FALSE);
			}else{
				records=new ArrayList<FindRpDjPredial>();
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void buscarPredio(){
		try{
			//records=new ArrayList<FindRpDjPredial>();
			records=new ArrayList<FindRpDjPredial>();
			
			
			//getRpDjpredial2(apellidosNombres, razonSocial, tipoDocIdentidad, numeroDocIdentidad, 
			//codigoPredio, tipoViaId, viaId, sectorId, lugarId, 
			//direccion, djId, personaId, 
			//codAntSatcaj, codigoAnterior, 
			//numeroCuadra, lado, numeroManzana, numeroVia, esPropietario)
			
			if(getCodigoPredio()!=null&&getCodigoPredio().trim().length()>0){				
				records=registroPrediosBo.getRpDjpredial3(null,null,null,null,getCodigoPredio(),null,null,null,null,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if(getPersonaId()!=null&&getPersonaId().intValue()>0){
				records=registroPrediosBo.getRpDjpredial3(null,null,null,null,null,null,null,null,null,null,null,getPersonaId(),null,null,null,null,null,null,Boolean.FALSE);
			}else if(getApellidosNombres()!=null&&getApellidosNombres().trim().length()>0){
				records=registroPrediosBo.getRpDjpredial3(getApellidosNombres(),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if(getRazonSocial()!=null&&getRazonSocial().trim().length()>0){
				records=registroPrediosBo.getRpDjpredial3(null,getRazonSocial(),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if(getTipoDocumento()!=null&&getTipoDocumento().trim().length()>0&&getNumeroDocumento()!=null&&getNumeroDocumento().trim().length()>0){
				Integer tipoDocIdentidadId=mapTipDocIdentidad.get(getTipoDocumento().toString());
				records=registroPrediosBo.getRpDjpredial3(null,null,tipoDocIdentidadId,getNumeroDocumento(),null,null,null,null,null,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if(getTipoVia()!=null&&getTipoVia().trim().length()>0
					&&getVia()!=null&&getVia().trim().length()>0){
				
				Integer tipoViaId=mapGnTipoVia.get(getTipoVia().trim());
				Integer viaId=mapGnVia.get(getVia().trim());
				
				records=registroPrediosBo.getRpDjpredial3(null,null,null,null,null,tipoViaId,viaId,null,null,null,null,null,null,null,numeroCuadra, lado, numeroManzana,numeroVia,Boolean.FALSE);
				
			}else if(getLugar()!=null&&getLugar().trim().length()>0
					&&getSector()!=null&&getSector().trim().length()>0){
				Integer sectorId=mapGnSector.get(getSector().trim());
				Integer lugarId=mapGnLugar.get(getLugar().trim());
				records=registroPrediosBo.getRpDjpredial3(null,null,null,null,null,null,null,sectorId,lugarId,null,null,null,null,null,null,null,null,null,Boolean.FALSE);
				
				if(getTipoVia()!=null&&getTipoVia().trim().length()>0
						&&getVia()!=null&&getVia().trim().length()>0){
					
					Integer tipoViaId=mapGnTipoVia.get(getTipoVia().trim());
					Integer viaId=mapGnVia.get(getVia().trim());
					
					records=registroPrediosBo.getRpDjpredial3(null,null,null,null,null,tipoViaId,viaId,sectorId,lugarId,null,null,null,null,null,numeroCuadra,lado,numeroManzana,numeroVia,Boolean.FALSE);
					
				}
			}else if(getDireccion()!=null&&getDireccion().trim().length()>0){
				records=registroPrediosBo.getRpDjpredial3(null,null,null,null,null,null,null,null,null,getDireccion(),null,null,null,null,null,null,null,null,Boolean.FALSE);
			}else if (getPersonaIdAnt()!=null&&!getPersonaIdAnt().isEmpty()){
				records=registroPrediosBo.getRpDjpredial3(null,null,null,null,null,null,null,null,null,null,null,null,getPersonaIdAnt(),null,null,null,null,null,Boolean.FALSE);
			}else if(getCodigoPredioAnt()!=null&&getCodigoPredioAnt().trim().length()>0){
				records=registroPrediosBo.getRpDjpredial3(null,null,null,null,null,null,null,null,null,null,null,null,null,getCodigoPredioAnt(),null,null,null,null,Boolean.FALSE);
			}else if(getNumeroManzana()!=null&&getNumeroManzana().intValue()>0){
				records=registroPrediosBo.getRpDjpredial3(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null, null,numeroManzana,null,Boolean.FALSE);
			}else{
				records=new ArrayList<FindRpDjPredial>();
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void selectTipoVia(ValueChangeEvent e){
		try{
			HtmlComboBox combo = (HtmlComboBox) e.getComponent();
			if(combo.getValue()!=null&&!combo.getValue().toString().isEmpty()){
				loadVia(combo.getValue().toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void loadVia(String value){
		try{
			if(value!=null){
				Integer tipoViaId=mapGnTipoVia.get(value);
				
				List<GnVia> lstGnVia=registroPrediosBo.findGnVia(tipoViaId);
				Iterator<GnVia> it = lstGnVia.iterator();
				lstvia = new ArrayList<SelectItem>();
				
		        while (it.hasNext()){
		        	GnVia obj = it.next();  
		        	lstvia.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getViaId())));
		        	mapGnVia.put(obj.getDescripcion(), obj.getViaId());
		        }	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//--
	public void selectSector(ValueChangeEvent e){
		try{
			HtmlComboBox combo = (HtmlComboBox) e.getComponent();
			if(combo.getValue()!=null&&!combo.getValue().toString().isEmpty()){
				loadLugar(combo.getValue().toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void loadLugar(String value){
		try{
			if(value!=null){
				Integer sectorId=mapGnSector.get(value);
				
				List<GnLugar> lstGnLugar=registroPrediosBo.findGnLugar(sectorId);
				Iterator<GnLugar> it = lstGnLugar.iterator();  
				lstlugar = new ArrayList<SelectItem>();
				 
		        while (it.hasNext()){
		        	GnLugar obj = it.next();  
		        	lstlugar.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getLugarId())));
		        	mapGnLugar.put(obj.getDescripcion(), obj.getLugarId());
		        }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	public void seleccion(){
		if(currentItem!=null){
			RegistroPredioManaged registro = (RegistroPredioManaged) getManaged("registroPredioManaged");
			registro.copiarDetalle(currentItem.getDjId());	
		}
	}
	*/
	
	/**
	 * Inscripcion de predio
	 * @return
	 */
	public String clonadj(){
		try{
			if(validaInscripcion(currentItem)){
				FacesUtil.closeSession("registroPredioManaged");//Elimina la sesion anterior
				if(currentItem!=null){
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_CLONE);
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",currentItem.getPredioId());
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",currentItem.getDjId());
					//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR INSCRIPCION
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_INSCRIPCION);
					getSessionManaged().setPage("/sisat/predial/registropredio.xhtml");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	
	public String clonadjFisca(){
		try{
			if(validaInscripcion(currentItem)){
				FacesUtil.closeSession("registroPredioManaged");//Elimina la sesion anterior
				if(currentItem!=null){
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_CLONE_FISCA);
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",currentItem.getPredioId());
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",currentItem.getDjId());
					//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR INSCRIPCION
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_INSCRIPCION);
					getSessionManaged().setPage("/sisat/predial/registropredio.xhtml");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	
	public Boolean validaInscripcion(FindRpDjPredial rpDjPredial){
		if(getSessionManaged().getContribuyente().getPersonaId()==rpDjPredial.getPersonaId()){
			WebMessages.messageError("No puede inscribir predio del mismo contribuyente");
			return Boolean.FALSE;
		}else{
			return Boolean.TRUE;	
		}
	}
	
	public String inscripcion(){
		FacesUtil.closeSession("registroPredioManaged");
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_NEW);
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",Constante.RESULT_PENDING);
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",Constante.RESULT_PENDING);
		//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR PRIMERA INSCRIPCION
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_PRIMERA_INSCRIPCION);
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "ES_FISCALIZACION","N");
		return sendRedirectPrincipal();
	}
	
	//caltamirano:ini
	public String inscripcionImpulsadaFisca(){
		FacesUtil.closeSession("registroPredioManaged");
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_NEW);
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",Constante.RESULT_PENDING);
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",Constante.RESULT_PENDING);
		//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR PRIMERA INSCRIPCION
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_PRIMERA_INSCRIPCION);
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "ES_FISCALIZACION","S");
		return sendRedirectPrincipal();
	}
	//caltamirano:fin
	
	
	public void exportarTablaExcel() {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Detalle_Busqueda_Predio");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("Codigo");
		row1.createCell(1).setCellValue("Apellidos y Nombres");
		row1.createCell(2).setCellValue("Tipo Doc.");
		row1.createCell(3).setCellValue("Nro. Doc.");
		row1.createCell(4).setCellValue("Tipo Predio");
		row1.createCell(5).setCellValue("Cod.Predio");
		row1.createCell(6).setCellValue("Nro. DJ");
		row1.createCell(7).setCellValue("Dirección");
		row1.createCell(8).setCellValue("Porc.");

		for (FindRpDjPredial data : records) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombres());
			row.createCell(columnIndex++).setCellValue(data.getDescripcionDocIdentidad());
			row.createCell(columnIndex++).setCellValue(data.getNumeroDocIdentidad());
			row.createCell(columnIndex++).setCellValue(data.getTipoPredioDesc());
			row.createCell(columnIndex++).setCellValue(data.getPredioId());
			row.createCell(columnIndex++).setCellValue(data.getDjId());
			row.createCell(columnIndex++).setCellValue(data.getDireccionCompleta());
			row.createCell(columnIndex++).setCellValue(String.valueOf(data.getPorcPropiedad().doubleValue()));


		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=busqueda_predio.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	
	public String getApellidosNombres() {
		return apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres.trim();
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getCodigoPredio() {
		return codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public FindRpDjPredial getFindRpDjPredial() {
		return findRpDjPredial;
	}

	public void setFindRpDjPredial(FindRpDjPredial findRpDjPredial) {
		this.findRpDjPredial = findRpDjPredial;
	}

	public ArrayList<FindRpDjPredial> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<FindRpDjPredial> records) {
		this.records = records;
	}

	public FindRpDjPredial getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(FindRpDjPredial currentItem) {
		this.currentItem = currentItem;
	}

	public List<SelectItem> getLstsector() {
		return lstsector;
	}

	public void setLstsector(List<SelectItem> lstsector) {
		this.lstsector = lstsector;
	}

	public List<SelectItem> getLstTipDocIdentidad() {
		return lstTipDocIdentidad;
	}

	public void setLstTipDocIdentidad(List<SelectItem> lstTipDocIdentidad) {
		this.lstTipDocIdentidad = lstTipDocIdentidad;
	}
	
	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}
	
	public List<SelectItem> getLstlugar() {
		return lstlugar;
	}

	public void setLstlugar(List<SelectItem> lstlugar) {
		this.lstlugar = lstlugar;
	}

	public List<SelectItem> getLsttipovia() {
		return lsttipovia;
	}

	public void setLsttipovia(List<SelectItem> lsttipovia) {
		this.lsttipovia = lsttipovia;
	}

	public List<SelectItem> getLstvia() {
		return lstvia;
	}

	public void setLstvia(List<SelectItem> lstvia) {
		this.lstvia = lstvia;
	}
	
	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	
	public int getScrollerPage() {
		return scrollerPage;
	}

	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}

	public String getCodigoPredioAnt() {
		return codigoPredioAnt;
	}

	public void setCodigoPredioAnt(String codigoPredioAnt) {
		this.codigoPredioAnt = codigoPredioAnt;
	}

	public Integer getNumeroCuadra() {
		return numeroCuadra;
	}

	public void setNumeroCuadra(Integer numeroCuadra) {
		this.numeroCuadra = numeroCuadra;
	}

	public Integer getLado() {
		return lado;
	}

	public void setLado(Integer lado) {
		this.lado = lado;
	}

	public Integer getNumeroManzana() {
		return numeroManzana;
	}

	public void setNumeroManzana(Integer numeroManzana) {
		this.numeroManzana = numeroManzana;
	}

	public Integer getSectorLugarId() {
		return sectorLugarId;
	}

	public void setSectorLugarId(Integer sectorLugarId) {
		this.sectorLugarId = sectorLugarId;
	}

	public String getValueComboBoxLado() {
		return valueComboBoxLado;
	}

	public void setValueComboBoxLado(String valueComboBoxLado) {
		this.valueComboBoxLado = valueComboBoxLado;
	}

	public List<SelectItem> getListLadoCuadra() {
		return listLadoCuadra;
	}

	public void setListLadoCuadra(List<SelectItem> listLadoCuadra) {
		this.listLadoCuadra = listLadoCuadra;
	}

	public String getNumeroVia() {
		return numeroVia;
	}

	public void setNumeroVia(String numeroVia) {
		this.numeroVia = numeroVia;
	}

	public String getPersonaIdAnt() {
		return personaIdAnt;
	}

	public void setPersonaIdAnt(String personaIdAnt) {
		this.personaIdAnt = personaIdAnt;
	}
}

