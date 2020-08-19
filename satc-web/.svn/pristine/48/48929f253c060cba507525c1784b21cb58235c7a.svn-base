package com.sat.sisat.caja.simulador.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.caja.dto.CjTipoDocumento;
import com.sat.sisat.caja.vo.CjAdministradoVo;
import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.common.managed.MensajeSisatDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.predial.dto.FindMpPersona;

@ManagedBean
@ViewScoped
public class CajaPersonaSimuladorManaged extends BaseManaged {

	private static final long serialVersionUID = 1071716540247319667L;

	@EJB
	CajaBoRemote cajaBo;
	
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	
	private HtmlComboBox cmbtipoDoc;
	private String nroDocumentoIdentidad;
	private Integer personaId;
	private String apellidoPat;
	private String apellidoMat;
	private String primer_nombre;
	private String segundo_nombre;
	private String razonSocial;
	private String apellidosNombres;
	private String codAnterior;

	private List<CjAdministradoVo> contribuyentes;
	private ArrayList<CjPersona> records;
	private CjPersona currentItem;

	private List<SelectItem> lstTipoDoc = new ArrayList<SelectItem>();

	private String nroPapeleta;
	private String nroPlaca;

	private Integer selectedOptBusc = 1;
	private Integer selectedOptBuscCodAnterior = 1;
	private Integer selectedOptBuscNroPlaca = 1;
	private Integer selectedOptBuscNroPapeleta = 1;
	private Integer selectedOptBuscApellidosNombres = 1;
	private Integer selectedOptBuscNroDoc = 1;
	
	private boolean contribYDosCajeros = false;

	private HashMap<String, Integer> mapMpTipoDocumento = new HashMap<String, Integer>();
	
	
	private List<Integer> deudasPapeletasSeleccionadas;
	private Integer deudaId;
	
	private Boolean todasPapeletas = Boolean.FALSE;
	

	public CajaPersonaSimuladorManaged() {
		getSessionManaged().setLinkRegresar("/sisat/caja/cajaUbicaPersona.xhtml");
	}

	@PostConstruct
	public void init() {
		try {
			List<CjTipoDocumento> lstTD = new ArrayList<CjTipoDocumento>();
			lstTD = cajaBo.obtenerTipoDocumento();
			Iterator<CjTipoDocumento> itAd = lstTD.iterator();
			while (itAd.hasNext()) {
				CjTipoDocumento objAd = itAd.next();
				lstTipoDoc.add(new SelectItem(objAd.getDescripcioncorta(),objAd.getTipoDoc() + ""));
				mapMpTipoDocumento.put(objAd.getDescripcioncorta(),objAd.getTipoDoc());
			}
			
			deudasPapeletasSeleccionadas = new ArrayList<Integer>();
			cajaBo.limpiarCjTmpDeudaCajero(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// para mostrar cajaCobranzaPapaleta //**para mostrar papletas (1)
	private CajaCobranzaPapeletaSimuladorManaged getCajaCobranzaPapeletaSimuladorManaged() {
		return (CajaCobranzaPapeletaSimuladorManaged) getManaged("cajaCobranzaPapeletaSimuladorManaged");
	}

	public void buscar() {
		records = new ArrayList<CjPersona>();
		try {
			int opcion = getSelectedOptBusc();
			String tipoDocuIdenDes;
			int tipoDocuIdenId = 0;
			String nroDocu = "";
			

			switch (opcion) {
			case 1:
				if(personaId==null || personaId.intValue()==0){
					addErrorMessage("Por favor ingrese el nuevo código del contribuyente");
					return;
				}
				break;
				
			case 7:
				if((codAnterior==null || codAnterior.isEmpty()) ){
					addErrorMessage("Por favor ingrese Código anterior");
					return;
				}
				break;
			case 2:
				if((apellidoPat==null || apellidoPat.isEmpty()) && (apellidoMat==null || apellidoMat.isEmpty()) && (primer_nombre==null || primer_nombre.isEmpty()) 
						&& (segundo_nombre==null || segundo_nombre.isEmpty())){
					addErrorMessage("Por favor ingrese al menos uno de los datos");
					return;
				}
				break;
			case 3:
				tipoDocuIdenDes = (String) getCmbtipoDoc().getValue();
				if(nroDocumentoIdentidad==null || nroDocumentoIdentidad.isEmpty()){
					addErrorMessage("Por favor, seleccione tipo y número de documento de identidad  para realizar la busqueda.");
					return;
				}
				tipoDocuIdenId = mapMpTipoDocumento.get(tipoDocuIdenDes);
				nroDocu = getNroDocumentoIdentidad();
				break;
			case 4:
				if(nroPapeleta==null || nroPapeleta.isEmpty()){
					addErrorMessage("Por favor, ingrese número de papeleta");
					return;
				}
				break;
			case 5:
				if(nroPlaca==null || nroPlaca.isEmpty()){
					addErrorMessage("Por favor, ingrese número de placa");
					return;
				}			
				break;
			case 6:
				if(apellidosNombres == null || apellidosNombres.isEmpty()){
					addErrorMessage("Por favor ingrese Nombres y Apellidos");
					return;
				}
				break;
			
			
			case 8:
				if(razonSocial == null || razonSocial.isEmpty()){
					addErrorMessage("Por favor ingrese razon social del contribuyente");
					return;
				}
				break;
			}
			
			if(personaId==null){
				setPersonaId(0);
			}
			if(codAnterior == null || codAnterior.isEmpty()){
				codAnterior =  "";
			}
			if(apellidoPat==null || apellidoPat.isEmpty()){
				apellidoPat = "";
			}
			if(apellidoMat==null || apellidoMat.isEmpty()){
				apellidoMat = "";
			}
			if(primer_nombre==null || primer_nombre.isEmpty()){
				primer_nombre = "";
			}
			if(segundo_nombre==null || segundo_nombre.isEmpty()){
				segundo_nombre = "";
			}
			if(nroDocu==null || nroDocu.isEmpty()){
				nroDocu = "";
			}
			if(nroPapeleta==null || nroPapeleta.isEmpty()){
				nroPapeleta = "";
			}
			if(nroPlaca==null || nroPlaca.isEmpty()){
				nroPlaca = "";
			}
			if(apellidosNombres == null || apellidosNombres.isEmpty()){
				apellidosNombres = "";
			}
						
			if(razonSocial == null || razonSocial.isEmpty()){
				razonSocial =  "";
			}
			records = cajaBo.obtenerPersona(personaId.intValue(), apellidoPat,apellidoMat, primer_nombre, segundo_nombre, razonSocial,tipoDocuIdenId, nroDocu, nroPapeleta, nroPlaca, apellidosNombres, codAnterior);
			fillContribuyentes();
			// SU STORE stpCJ_sel_Contribuyente
			
			/** Reiniciando papeletas seleccionadas*/
			deudasPapeletasSeleccionadas.clear();
			
		} catch (Exception ex) {
			String msg = "No ha sido posible realizar la busqueda.";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}

	public void changeOpcionBusc(ActionEvent ev) {
		
		limpiarBusc();
	}

	public void limpiarBusc() {		
		
		/** Cod. Nuevo */
		if (selectedOptBusc != 1) {
			setPersonaId(null);
		}

		setApellidoPat(null);
		setApellidoMat(null);
		setPrimer_nombre(null);
		setSegundo_nombre(null);
		setCmbtipoDoc(null);

		/** Nro. Doc. Identidad */
		if (selectedOptBusc != 3) {
			setNroDocumentoIdentidad(null);
		}

		/** Nro. Papeleta */
		if (selectedOptBusc != 4) {
			setNroPapeleta(null);
		}

		/** Nro. Placa */
		if (selectedOptBusc != 5) {
			setNroPlaca(null);
		}

		/** Apellidos/Nombres */
		if (selectedOptBusc != 6) {
			setApellidosNombres(null);
		}

		/** Cod. Anterior */
		if (selectedOptBusc != 7) {
			setCodAnterior(null);
		}
		setRazonSocial(null);
	}

	public String seleccionaPersona() {
		try {
			if (currentItem != null) {
				getSessionMap().put("cjPersona", currentItem);

				FindMpPersona contrib = new FindMpPersona();
				contrib.setPersonaId(currentItem.getPersonaId());
				contrib.setApellidosNombres(currentItem.getNombreCompleto());
				contrib.setDomicilioPersona(currentItem.getDomicilio());
				getSessionManaged().setContribuyente(contrib);
				getSessionManaged().setTributoPage(false); // No muestre barra para caja				
				
				
				String contribEnConsulta = cajaBo.busquedaContribEnAtencion(getSessionManaged().getUsuarioLogIn().getUsuarioId(), currentItem.getPersonaId(), null);
				
				Boolean resp = cobranzaCoactivaBo.contribEnCobranzaCoactiva(currentItem.getPersonaId()); 
				if(resp.booleanValue()){
					MensajeSisatDTO mensajeSisatDTO = new MensajeSisatDTO();
					mensajeSisatDTO.setMensaje("El contribuyente "+currentItem.getPersonaId()+
							" esta en un proceso de Cobranza Coactiva");
					mensajeSisatDTO.setTipoColor(1);
					getSessionManaged().anhadirMensaje(mensajeSisatDTO);
				}				
				if(contribEnConsulta != null){
					MensajeSisatDTO mensajeSisatDTO = new MensajeSisatDTO();
					mensajeSisatDTO.setMensaje("El contribuyente "+currentItem.getPersonaId()+
							" esta SIENDO ATENDIDO por el Usuario: "+contribEnConsulta);
					mensajeSisatDTO.setTipoColor(2);
					getSessionManaged().anhadirMensaje(mensajeSisatDTO);
				}
				
				
				
				
			} else {
				// selecciones
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
		
		sendRedirectPrincipalListener();
		return null;
	}
	
	public String cobrarPapeleta(){
		
		// limpiando papeletas seleccionadas
		
		deudasPapeletasSeleccionadas.clear();
		deudasPapeletasSeleccionadas.add(currentItem.getDeudaId());
		
		
		try{
			if(currentItem != null){
				getSessionMap().put("cjPersona", currentItem);
				getSessionMap().put("deudasPapeletasSeleccionadas", deudasPapeletasSeleccionadas);
				
				getCajaCobranzaPapeletaSimuladorManaged().iniciarDatosDefault();
			}
		}catch(Exception ex){
			String msg = "Ha ocurrido un error";
			System.out.println(msg+ex);
		}
		return null;
	}
	
	public void cobrarPapeletas(){
		/**Limpiando las papeletas seleccionadas anteriormente*/
		deudasPapeletasSeleccionadas.clear();
		
		/** Agregando las nuevas papeletas seleccionadas*/
		for(CjPersona cjPersona : records){
			if (cjPersona.getSeleccionado() != null)
			{
				if(cjPersona.getSeleccionado()){
					deudasPapeletasSeleccionadas.add(cjPersona.getDeudaId());
				}else{
					deudasPapeletasSeleccionadas.remove(cjPersona.getDeudaId());
				}			
			}
		}
		
		/** Estableciendo la condicion de que al menos se haya seleccionado una papeleta*/
		if(deudasPapeletasSeleccionadas.size() != 0){
			getSessionMap().put("deudasPapeletasSeleccionadas", deudasPapeletasSeleccionadas);			
			getCajaCobranzaPapeletaSimuladorManaged().iniciarDatosDefault();			
		}else{
			addErrorMessage("Seleccione al menos una papeleta");
		}		
	}
	
	private void fillContribuyentes(){
		contribuyentes = new ArrayList<CjAdministradoVo>();
		
		Set<Integer> perIds = new HashSet<Integer>();
		for(CjPersona per: records){
			perIds.add(per.getPersonaId());
		}
		for(Integer id : perIds){
			String persId = "";
			String nomComp = "";
			String tipoDocIdenDes = "";
			String numDocIden = "";
			
			List<CjPersona> lstPer = new ArrayList<CjPersona>();
			for(CjPersona per: records){
				if(id.intValue() == per.getPersonaId()){
					persId = String.valueOf(per.getPersonaId());
					nomComp = per.getNombreCompleto();
					tipoDocIdenDes = per.getTipoDocuIdenDes();
					numDocIden = per.getNroDocuIden();
					lstPer.add(per);
				}
			}
			CjAdministradoVo cja = new CjAdministradoVo(persId, nomComp, tipoDocIdenDes, numDocIden);
			cja.setPapeletas(lstPer);
			contribuyentes.add(cja);
		}
	}
	
	public void agregarPapeleta(){
		
		
		//deudasPapeletasSeleccionadas.add(deudaId);
		getSessionMap().put("cjPersona", currentItem);
		
	}	

	public void valueChangeListenerTodasPapeletas(ValueChangeEvent ev){
		
		String nv = ev.getNewValue().toString();
		if(nv.equals("true")){
			for(CjPersona persona:records){
				if(persona.getDeudaId() != null && persona.getDeudaId() > 0){
					persona.setSeleccionado(Boolean.TRUE);
					deudasPapeletasSeleccionadas.add(persona.getDeudaId());
				}				
			}
		}else{
			for(CjPersona persona:records){
				if(persona.getDeudaId() != null && persona.getDeudaId() > 0){
					persona.setSeleccionado(Boolean.FALSE);
					deudasPapeletasSeleccionadas.remove((persona.getDeudaId()));
				}				
			}
		}
		
	}
	
	public void liberarContribuyente(){
		
		try {
			this.contribYDosCajeros = false;
			cajaBo.limpiarCjTmpDeudaCajero(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			this.buscar();
		} catch (SisatException e) {
			
			WebMessages.messageError(e.getMessage());
		}
		
	}	
	
	public HtmlComboBox getCmbtipoDoc() {
		return cmbtipoDoc;
	}

	public void setCmbtipoDoc(HtmlComboBox cmbtipoDoc) {
		this.cmbtipoDoc = cmbtipoDoc;
	}

	public List<SelectItem> getLstTipoDoc() {
		return lstTipoDoc;
	}

	public ArrayList<CjPersona> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<CjPersona> record) {
		this.records = record;
	}

	public String getNroDocumentoIdentidad() {
		return nroDocumentoIdentidad;
	}

	public void setNroDocumentoIdentidad(String nroDocumentoIdentidad) {
		if(nroDocumentoIdentidad!=null){
			this.nroDocumentoIdentidad = nroDocumentoIdentidad.trim();
		}
		
		this.nroDocumentoIdentidad = nroDocumentoIdentidad;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public CjPersona getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(CjPersona currentItem) {
		this.currentItem = currentItem;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		if(nroPapeleta!=null){
			this.nroPapeleta = nroPapeleta.trim();
			
		}
		this.nroPapeleta = nroPapeleta;
	}
		
	public String getNroPlaca() {
		return nroPlaca;
	}

	public void setNroPlaca(String nroPlaca) {
		this.nroPlaca = nroPlaca;
	}

	public Integer getSelectedOptBusc() {
		return selectedOptBusc;
	}

	public void setSelectedOptBusc(Integer selectedOptBusc) {
		this.selectedOptBusc = selectedOptBusc;
	}

	public String getApellidoPat() {
		return apellidoPat;
	}

	public void setApellidoPat(String apellidoPat) {
		
		if(apellidoPat !=null){
			apellidoPat = apellidoPat.trim();
		}
		
		this.apellidoPat = apellidoPat;
	}

	public String getApellidoMat() {
		return apellidoMat;
	}

	public void setApellidoMat(String apellidoMat) {
		if(apellidoMat !=null){
			apellidoMat = apellidoMat.trim();
		}
		this.apellidoMat = apellidoMat;
	}

	public String getPrimer_nombre() {
		return primer_nombre;
	}

	public void setPrimer_nombre(String primer_nombre) {
		if(primer_nombre !=null){
			primer_nombre = primer_nombre.trim();
		}
		
		this.primer_nombre = primer_nombre;
	}

	public String getSegundo_nombre() {
		return segundo_nombre;
	}

	public void setSegundo_nombre(String segundo_nombre) {
		if(segundo_nombre !=null){
			segundo_nombre = segundo_nombre.trim();
		}
		this.segundo_nombre = segundo_nombre;
	}

	public List<CjAdministradoVo> getContribuyentes() {
		return contribuyentes;
	}

	public void setContribuyentes(List<CjAdministradoVo> contribuyentes) {
		this.contribuyentes = contribuyentes;
	}

	public String getApellidosNombres() {
		return apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		if(apellidosNombres !=null){
			apellidosNombres = apellidosNombres.trim();
		}
		this.apellidosNombres = apellidosNombres;
	}

	public String getCodAnterior() {
		return codAnterior;
	}

	public void setCodAnterior(String codAnterior) {
		if(codAnterior !=null){
			codAnterior = codAnterior.trim();
		}
		this.codAnterior = codAnterior;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		if(razonSocial !=null){
			razonSocial = razonSocial.trim();
		}
		this.razonSocial = razonSocial;
	}

	public Integer getSelectedOptBuscCodAnterior() {
		return selectedOptBuscCodAnterior;
	}

	public void setSelectedOptBuscCodAnterior(Integer selectedOptBuscCodAnterior) {
		this.selectedOptBuscCodAnterior = selectedOptBuscCodAnterior;
	}

	public Integer getSelectedOptBuscNroPlaca() {
		return selectedOptBuscNroPlaca;
	}

	public void setSelectedOptBuscNroPlaca(Integer selectedOptBuscNroPlaca) {
		this.selectedOptBuscNroPlaca = selectedOptBuscNroPlaca;
	}

	public Integer getSelectedOptBuscNroPapeleta() {
		return selectedOptBuscNroPapeleta;
	}

	public void setSelectedOptBuscNroPapeleta(Integer selectedOptBuscNroPapeleta) {
		this.selectedOptBuscNroPapeleta = selectedOptBuscNroPapeleta;
	}

	public Integer getSelectedOptBuscApellidosNombres() {
		return selectedOptBuscApellidosNombres;
	}

	public void setSelectedOptBuscApellidosNombres(Integer selectedOptBuscApellidosNombres) {
		this.selectedOptBuscApellidosNombres = selectedOptBuscApellidosNombres;
	}

	public Integer getSelectedOptBuscNroDoc() {
		return selectedOptBuscNroDoc;
	}

	public void setSelectedOptBuscNroDoc(Integer selectedOptBuscNroDoc) {
		this.selectedOptBuscNroDoc = selectedOptBuscNroDoc;
	}

	public boolean isContribYDosCajeros() {
		return contribYDosCajeros;
	}

	public void setContribYDosCajeros(boolean contribYDosCajeros) {
		this.contribYDosCajeros = contribYDosCajeros;
	}

	public Integer getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(Integer deudaId) {
		this.deudaId = deudaId;
	}

	public Boolean getTodasPapeletas() {
		return todasPapeletas;
	}

	public void setTodasPapeletas(Boolean todasPapeletas) {
		this.todasPapeletas = todasPapeletas;
	}
}
