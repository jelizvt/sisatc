package com.sat.sisat.estadocuenta.managed;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.estadocuenta.business.EstadoCuentaBoRemote;
import com.sat.sisat.estadocuenta.dto.CrConstanciaNoAdeudo;
import com.sat.sisat.estadocuenta.dto.CrConstanciaNoAdeudoDTO;
import com.sat.sisat.estadocuenta.dto.CrGeneralDTO;
import com.sat.sisat.estadocuenta.dto.CrSubConceptoDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.GnSubconcepto;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.MpRequerimientoCondicionEspecialDTO;
import com.sat.sisat.vehicular.dto.CarteraVehiculosDTO;



@ManagedBean
@ViewScoped
public class RegistrarConstanciaNoAdeudo extends BaseManaged implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	EstadoCuentaBoRemote estadocuentaBo;
	
	private Integer conceptoId;
	private String concepto;
	private List<SelectItem> listConcepto  = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapConcepto= new HashMap<String, Integer>();
	
	private Integer subconceptoId;
	private String[] subconcepto=null; //private String subconcepto;private String[] lstMpTipoPersonaSeleccionados = null;
	private List<CrSubConceptoDTO>listSub= null;//private List<SelectItem> listSubConcepto  = new ArrayList<SelectItem>();  private List<MpTipoPersona> lstTipoPersona = null;
	private HashMap<String, Integer> mapSubConcepto= new HashMap<String, Integer>();
			
	private Integer anio;
	private Integer personaId;
	private List<DeudaDTO> listDeudas = new ArrayList<DeudaDTO>();
		
	private String[] anioSeleccionado = null;//lstMpTipoPersonaSeleccionados
	private List<CrGeneralDTO> listAnio = null;//lstTipoPersona
	private HashMap<String, Integer> mapAnio = new HashMap<String, Integer>();//mapMpTipoPersonaSeleccionados
	
	private String[] cuotaSeleccionado = null;
	private List<CrGeneralDTO> listCuota = null;
	private HashMap<String, Integer> mapCuota = new HashMap<String, Integer>();
	
	private String[] propiedadSeleccionado = null;
	private List<CrGeneralDTO> listPropiedad = null;
	private HashMap<String, Integer> mapPropiedad = new HashMap<String, Integer>();
	
	private Boolean sinDeuda;
	String subconceptoSelect="";
	String annioSelect="";
	String cuotaSelect="";
	String predioSelect="";
	private List<CrConstanciaNoAdeudoDTO> listConstancia = new ArrayList<CrConstanciaNoAdeudoDTO>();
	private CrConstanciaNoAdeudoDTO constanciaItem= new CrConstanciaNoAdeudoDTO();
	
	private boolean selectedAllSubconcepto;
	private boolean selectedAllPropiedades;
	
	private int tipoConstancia;
	
	private String selectionMode = "Single";
	private Collection<Object> selection;
	
	
	
	@PostConstruct
	public void init() throws Exception {
		try {
			tipoConstancia = 1 ; 
			
			/* COMBOBOX:: RECIBO */
			List<CrConstanciaNoAdeudoDTO> lstGnConcepto = estadocuentaBo.obtenerReciboConstancia(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			Iterator<CrConstanciaNoAdeudoDTO> iter = lstGnConcepto.iterator();
			listConcepto = new ArrayList<SelectItem>();
			
			while (iter.hasNext()) {
				CrConstanciaNoAdeudoDTO obj = iter.next();
				listConcepto.add(new SelectItem(obj.getNumeroRecibo(), String.valueOf(obj.getPersonaId())));//String.valueOf(obj.getReciboId())
				mapConcepto.put(obj.getNumeroRecibo().trim(),obj.getPersonaId());//obj.getReciboId()
			}
			
			//finMpPersonaItem= new FindMpPersona();
			Object objtemp = getSessionMap().get("recibo");
			if (objtemp != null) {
				System.out.println(objtemp);

				//finMpPersonaItem = (FindMpPersona)objtemp;
				//getSessionMap().remove("finMpPersonaItem");
				//mpPersona = new MpPersona();
				
				//mpPersona = personaBo.getMpPersona(finMpPersonaItem.getPersonaId());
				//fechaNacoConst=mpPersona.getFechaDeclaracion();
				//tipoAccion=Constante.TIPO_ACCION_EDITAR;
//				if(mpPersona.getFlagEstatal() != null){
//					flagEstatal = mpPersona.getFlagEstatal().equals(Constante.ESTADO_ACTIVO)? Boolean.TRUE : Boolean.FALSE;
//					int flagEst = flagEstatal? 1:0;
//					
//					if(flagEst == 0)
//					{
//						this.setSeleccionTipoEmpresa("No Estatal");
//						flagEstatal = Boolean.FALSE;
//					}
//					else
//					{
//						this.setSeleccionTipoEmpresa("Estatal");
//						flagEstatal = Boolean.TRUE;
//					}					 
//					
//				}else{					
//					flagEstatal = Boolean.FALSE;
//				}
//				obtenerDatos();
			
				 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*public void selectionListener(AjaxBehaviorEvent event) {
        AbstractExtendedDataTable dataTable = (AbstractExtendedDataTable) event.getComponent();
        Object originalKey = dataTable.getRowKey();
        selectionItems.clear();
        for (Object selectionKey : selection) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
                selectionItems.add((InventoryItem) dataTable.getRowData());
            }
        }
        dataTable.setRowKey(originalKey);
    }*/
	
	public int buscar() {
		int resp = 0;
		try {
			
			cuotaSelect="";
			annioSelect = "";
			subconceptoSelect = "";
			predioSelect = "";
			
			Iterator<CrSubConceptoDTO> it2 = listSub.iterator();
			while (it2.hasNext()) {
				CrSubConceptoDTO obj = it2.next();
				if(obj.isSeleted()) {
					subconceptoSelect=subconceptoSelect + obj.getId() + ",";
				}
			}
			
			Iterator<CrGeneralDTO> it3 = listAnio.iterator();
			while (it3.hasNext()) {
				CrGeneralDTO obj = it3.next();
				if(obj.isSeleted()) {
					annioSelect=annioSelect + obj.getDescripcion() + ",";//mapAnio.get
				}
			}
			
			Iterator<CrGeneralDTO> it4 = listCuota.iterator();
			while (it4.hasNext()) {
				CrGeneralDTO obj = it4.next();
				if(obj.isSeleted()) {
					cuotaSelect=cuotaSelect + obj.getDescripcion()+ ",";
				}
			}
			
			Iterator<CrGeneralDTO> it5 = listPropiedad.iterator();
			while (it5.hasNext()) {
				CrGeneralDTO obj = it5.next();
				if(obj.isSeleted()) {
					predioSelect=predioSelect + obj.getDescripcion()+ ",";
				}
			}
			
			System.out.println("personaId : "+personaId);
			System.out.println("subconceptoSelect : "+subconceptoSelect);
			System.out.println("annioSelect : "+annioSelect);
			System.out.println("predioSelect : "+cuotaSelect);
			System.out.println("predioSelect : "+predioSelect);
			if(this.tipoConstancia == 2) {
				if(cuotaSelect == "" || annioSelect == "" || subconceptoSelect == "" || predioSelect == "") {
					resp = 3;
					return resp;
				}
			}
			if(this.tipoConstancia == 1) {
				if(annioSelect == "" || subconceptoSelect == "" || predioSelect == "") {
					resp = 4;
					return resp;
				}
			}
			listDeudas = estadocuentaBo.obtenerDeuda(personaId,subconceptoSelect,annioSelect,cuotaSelect,predioSelect);
			
			if (listDeudas.size()!=0){
				resp = 1;
				//addErrorMessage(getMsg("El Contribuyente mantiene deuda en los conceptos seleccionados."));
				return resp;
			}else{
				resp=0;
				setSinDeuda(Boolean.TRUE);
				return resp;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
		
	}
	
	public void guardar() {
		try {
			if(listDeudas.size()==0){
				Integer resultado=estadocuentaBo.actualizarConstancia(personaId, annioSelect, subconceptoSelect,predioSelect,getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
				if (resultado!=null && resultado==Constante.RESULT_SUCCESS){
					listConstancia = estadocuentaBo.obtenerConstancia(0, personaId);
				}
			}
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
	}
	
	public String salir() {
		//getSessionMap().remove("findCcLoteDeItem");
		return sendRedirectPrincipal();
	}
	
	  public void loadGnConceptoById(ValueChangeEvent event) {
			try{
				   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
				    String value=combo.getValue().toString();
					if(value!=null){
						personaId=(Integer)mapConcepto.get(value);
					}
					viewdetalle();
					
			}catch(Exception e){
				e.printStackTrace();
				WebMessages.messageFatal(e);			
			}
		}
		
	  public void viewdetalle(){ 
		  try{ 
			  //System.out.println("ENTRE CARGARRRR");
			  /* LISTBOX:: SUBCONCEPTO */
				String subconceptoSeleccionado="";
				subconcepto = new String[] {};
			    
			    listSub = estadocuentaBo.obtenerSubConcepto(personaId);
			    
				Iterator<CrSubConceptoDTO> it2 = listSub.iterator();
				while (it2.hasNext()) {
					CrSubConceptoDTO obj = it2.next();
					//System.out.println("--->"+obj.getDescripcion());
					obj.setSeleted(false);
				}
				
			    /*Iterator<GnSubconcepto> it2 = listSub.iterator();
				while (it2.hasNext()) 
				{
					GnSubconcepto obj = it2.next();
					mapSubConcepto.put(obj.getAbreviacion(),obj.getId().getSubconceptoId());
					subconceptoSeleccionado = subconceptoSeleccionado + obj.getId().getSubconceptoId()+ ",";
				}
				subconcepto = subconceptoSeleccionado.split(",");*/
				
			/* LISTBOX:: AÑOS */
				String anioSelect = "";
				anioSeleccionado = new String[] {};
				
				listAnio = estadocuentaBo.obtenerAnio(personaId);
				
				Iterator<CrGeneralDTO> it3 = listAnio.iterator();
				while (it3.hasNext()) {
					CrGeneralDTO obj = it3.next();
					obj.setSeleted(false);
				}
				
				
				/*Iterator<CrConstanciaNoAdeudo> it3 = listAnio.iterator();
				while (it3.hasNext()) {
					CrConstanciaNoAdeudo obj = it3.next();
					mapAnio.put(obj.getAnio(),obj.getConceptoId());
					anioSelect = anioSelect + obj.getAnio() + ",";
				}
				anioSeleccionado = anioSelect.split(",");*/
				
			/* LISTBOX:: CUOTAS */
				String cuotaSelect = "";
				cuotaSeleccionado = new String[] {};
				
				listCuota = estadocuentaBo.obtenerCuota(personaId);
				
				Iterator<CrGeneralDTO> it4 = listCuota.iterator();
				while (it4.hasNext()) {
					CrGeneralDTO obj = it4.next();
					obj.setSeleted(false);
				}
				
				/*Iterator<CrConstanciaNoAdeudo> it4 = listCuota.iterator();
				while (it4.hasNext()) {
					CrConstanciaNoAdeudo obj = it4.next();
					mapCuota.put(obj.getNumero(),obj.getReciboId());
					cuotaSelect = cuotaSelect + obj.getNumero() + ",";
				}
				cuotaSeleccionado = cuotaSelect.split(",");*/
				
			/* LISTBOX:: PROPIEDADES */
				String propSelect = "";
				propiedadSeleccionado = new String[] {};
				
				listPropiedad = estadocuentaBo.obtenerPropiedad(personaId);
				
				Iterator<CrGeneralDTO> it5 = listPropiedad.iterator();
				while (it5.hasNext()) {
					CrGeneralDTO obj = it5.next();
					obj.setSeleted(false);
				}
				
				/*Iterator<CrConstanciaNoAdeudo> it5 = listPropiedad.iterator();
				while (it5.hasNext()) {
					CrConstanciaNoAdeudo obj = it5.next();
					mapPropiedad.put(obj.getReferencia(),obj.getPersonaId());
					propSelect = propSelect + obj.getReferencia() + ",";
				}
				propiedadSeleccionado = propSelect.split(",");*/
		  }catch(Exception e){
	  			e.printStackTrace();
	  			WebMessages.messageFatal(e);			
		  }
	}
	
	  public void impresion() {
			try {

				String cadena = null;
				java.sql.Connection connection = null;
				cadena = "reporte_constacia_no_adeudo.jasper";

				CrudServiceBean connj = CrudServiceBean.getInstance();
				connection = connj.connectJasper();
				String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
				String path_report = path_context + "/sisat/reportes/";
				String path_imagen = path_context + "/sisat/reportes/imagen/";
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				
				Integer persona = constanciaItem.getPersonaId();
				
				Integer recibo = constanciaItem.getReciboId();
				
				parameters.put("persona_id", persona);
				parameters.put("recibo_id", recibo);
				parameters.put("ruta_imagen", path_imagen);
				parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						(SATWEBParameterFactory.getPathReporte() + cadena),
						parameters, connection);
				
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				HttpServletResponse response = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext()
						.getResponse();
					JasperExportManager.exportReportToPdfStream(jasperPrint,output);
				
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition","attachment;filename=" + cadena.replaceAll(".jasper", ".pdf"));

				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response.getOutputStream();
				
				servletOutputStream.write(output.toByteArray(), 0,output.size());
				
				servletOutputStream.flush();
				servletOutputStream.close();
				FacesContext.getCurrentInstance().responseComplete();
			
			
			} catch (Exception e) {
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
		}
	  
  	public void changeSelectAllpropiededades(ValueChangeEvent ev){
		String nv = ev.getNewValue().toString();
		System.out.println(nv);
		if(nv.equals("true")){
			for(CrGeneralDTO de : listPropiedad){
				de.setSeleted(true);
			}
		}else{
			for(CrGeneralDTO de : listPropiedad){
				de.setSeleted(false);
			}
		}
  	}
  	
  	public void changeSelectsubconcepto(ValueChangeEvent ev) {
  		String nv = ev.getNewValue().toString();
		System.out.println(nv);
  		//System.out.println(item.getAbreviacion());
  	}
	
	public HashMap<String, Integer> getMapSubConcepto() {
		return mapSubConcepto;
	}

	public void setMapSubConcepto(HashMap<String, Integer> mapSubConcepto) {
		this.mapSubConcepto = mapSubConcepto;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public List<SelectItem> getListConcepto() {
		return listConcepto;
	}

	public void setListConcepto(List<SelectItem> listConcepto) {
		this.listConcepto = listConcepto;
	}

	public HashMap<String, Integer> getMapConcepto() {
		return mapConcepto;
	}

	public void setMapConcepto(HashMap<String, Integer> mapConcepto) {
		this.mapConcepto = mapConcepto;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public List<DeudaDTO> getListDeudas() {
		return listDeudas;
	}

	public void setListDeudas(List<DeudaDTO> listDeudas) {
		this.listDeudas = listDeudas;
	}

	public Integer getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public List<CrSubConceptoDTO> getListSub() {
		return listSub;
	}

	public void setListSub(List<CrSubConceptoDTO> listSub) {
		this.listSub = listSub;
	}

	public EstadoCuentaBoRemote getEstadocuentaBo() {
		return estadocuentaBo;
	}

	public void setEstadocuentaBo(EstadoCuentaBoRemote estadocuentaBo) {
		this.estadocuentaBo = estadocuentaBo;
	}

	public String[] getSubconcepto() {
		return subconcepto;
	}

	public void setSubconcepto(String[] subconcepto) {
		this.subconcepto = subconcepto;
	}

	public String[] getAnioSeleccionado() {
		return anioSeleccionado;
	}

	public void setAnioSeleccionado(String[] anioSeleccionado) {
		this.anioSeleccionado = anioSeleccionado;
	}

	public List<CrGeneralDTO> getListAnio() {
		return listAnio;
	}

	public void setListAnio(List<CrGeneralDTO> listAnio) {
		this.listAnio = listAnio;
	}

	public HashMap<String, Integer> getMapAnio() {
		return mapAnio;
	}

	public void setMapAnio(HashMap<String, Integer> mapAnio) {
		this.mapAnio = mapAnio;
	}

	public String[] getCuotaSeleccionado() {
		return cuotaSeleccionado;
	}

	public void setCuotaSeleccionado(String[] cuotaSeleccionado) {
		this.cuotaSeleccionado = cuotaSeleccionado;
	}

	public List<CrGeneralDTO> getListCuota() {
		return listCuota;
	}

	public void setListCuota(List<CrGeneralDTO> listCuota) {
		this.listCuota = listCuota;
	}

	public HashMap<String, Integer> getMapCuota() {
		return mapCuota;
	}

	public void setMapCuota(HashMap<String, Integer> mapCuota) {
		this.mapCuota = mapCuota;
	}

	public String[] getPropiedadSeleccionado() {
		return propiedadSeleccionado;
	}

	public void setPropiedadSeleccionado(String[] propiedadSeleccionado) {
		this.propiedadSeleccionado = propiedadSeleccionado;
	}

	public List<CrGeneralDTO> getListPropiedad() {
		return listPropiedad;
	}

	public void setListPropiedad(List<CrGeneralDTO> listPropiedad) {
		this.listPropiedad = listPropiedad;
	}

	public HashMap<String, Integer> getMapPropiedad() {
		return mapPropiedad;
	}

	public void setMapPropiedad(HashMap<String, Integer> mapPropiedad) {
		this.mapPropiedad = mapPropiedad;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Boolean getSinDeuda() {
		return sinDeuda;
	}

	public void setSinDeuda(Boolean sinDeuda) {
		this.sinDeuda = sinDeuda;
	}

	public List<CrConstanciaNoAdeudoDTO> getListConstancia() {
		return listConstancia;
	}

	public void setListConstancia(List<CrConstanciaNoAdeudoDTO> listConstancia) {
		this.listConstancia = listConstancia;
	}

	public CrConstanciaNoAdeudoDTO getConstanciaItem() {
		return constanciaItem;
	}

	public void setConstanciaItem(CrConstanciaNoAdeudoDTO constanciaItem) {
		this.constanciaItem = constanciaItem;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public String getSubconceptoSelect() {
		return subconceptoSelect;
	}

	public void setSubconceptoSelect(String subconceptoSelect) {
		this.subconceptoSelect = subconceptoSelect;
	}

	public String getAnnioSelect() {
		return annioSelect;
	}

	public void setAnnioSelect(String annioSelect) {
		this.annioSelect = annioSelect;
	}

	public String getPredioSelect() {
		return predioSelect;
	}

	public void setPredioSelect(String predioSelect) {
		this.predioSelect = predioSelect;
	}

	public boolean isSelectedAllSubconcepto() {
		return selectedAllSubconcepto;
	}

	public void setSelectedAllSubconcepto(boolean selectedAllSubconcepto) {
		this.selectedAllSubconcepto = selectedAllSubconcepto;
	}

	public boolean isSelectedAllPropiedades() {
		return selectedAllPropiedades;
	}

	public void setSelectedAllPropiedades(boolean selectedAllPropiedades) {
		this.selectedAllPropiedades = selectedAllPropiedades;
	}

	public int getTipoConstancia() {
		return tipoConstancia;
	}

	public void setTipoConstancia(int tipoConstancia) {
		this.tipoConstancia = tipoConstancia;
	}

	public String getCuotaSelect() {
		return cuotaSelect;
	}

	public void setCuotaSelect(String cuotaSelect) {
		this.cuotaSelect = cuotaSelect;
	}

	public String getSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}

	public Collection<Object> getSelection() {
		return selection;
	}

	public void setSelection(Collection<Object> selection) {
		this.selection = selection;
	}

}
