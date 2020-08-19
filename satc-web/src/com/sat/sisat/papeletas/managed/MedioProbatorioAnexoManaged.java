package com.sat.sisat.papeletas.managed;

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
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.alfresco.content.PapeletaInfraccion;
import com.sat.sisat.alfresco.util.Util;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.persistence.entity.PaDocuAnexo;
import com.sat.sisat.persistence.entity.PaMedioProbatorio;

@ManagedBean
@ViewScoped
			 
public class MedioProbatorioAnexoManaged extends BaseManaged {
	@EJB
	PapeletaBoRemote papeletaBo;
	
	private FileUpload archivo;
	private PaDocuAnexo paDocuAnexo;
	
	private HtmlComboBox cmbTipoDocumento;
	private List<SelectItem> lstTipoDocumento=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnTipoDocumento=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnTipoDocumento=new HashMap<Integer,String>();
	private String cmbValueTipoDocumento;
	
	public MedioProbatorioAnexoManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			List<Object> lMedioProbatorio=papeletaBo.getAllPaMedioProbatorio();
			Iterator<Object> it = lMedioProbatorio.iterator();  
			lstTipoDocumento = new ArrayList<SelectItem>();
			
	        while (it.hasNext()){
	        	PaMedioProbatorio obj = (PaMedioProbatorio)it.next();  
	        	lstTipoDocumento.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getMedioProbatorioId())));
	        	mapGnTipoDocumento.put(obj.getDescripcion(), obj.getMedioProbatorioId());
	        	mapIGnTipoDocumento.put(obj.getMedioProbatorioId(),obj.getDescripcion());
	        }
	        
	        paDocuAnexo=new PaDocuAnexo();
	        
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}

	public void guardar(){
		try{
			getPaDocuAnexo().setTipoDocumentoId(mapGnTipoDocumento.get(cmbValueTipoDocumento));
			getPaDocuAnexo().setEstado(Constante.ESTADO_ACTIVO);
			getPaDocuAnexo().setTipoDocumento(cmbValueTipoDocumento);
			if(archivo!=null&&archivo.getFile()!=null){
				getPaDocuAnexo().setReferencia(archivo.getFileName());
				getPaDocuAnexo().setContentId(new BigDecimal(archivo.getContentId()));
				//INICIO ITANTAMANGO
				/*PapeletaInfraccion pi=new PapeletaInfraccion(archivo.getContentId()+archivo.getFileName(),Util.getBytesFromFile(archivo.getFile()),String.valueOf(archivo.getContentId()));
				RepositoryManager.guardarContenido(pi);*/
				//FIN ITANTAMANGO
			}else{
				getPaDocuAnexo().setReferencia(null);
				getPaDocuAnexo().setContentId(null);
			}
			
			RegistroPapeletasManaged registroPapeleta = (RegistroPapeletasManaged) getManaged("registroPapeletasManaged");
			if(registroPapeleta!=null){
				registroPapeleta.addDocumentosAnexos(getPaDocuAnexo());
				limpiar();
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	public void setProperty(FileUpload archivo){
		setArchivo(archivo);
	}
	
	public void setParameters(){
		String paramParentFileUpload = FacesUtil.getRequestParameter("paramParentFileUpload");
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(),"paramParentFileUpload",paramParentFileUpload);
	}
	
	public void salir(){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void limpiar(){
		paDocuAnexo=new PaDocuAnexo();
		if(cmbTipoDocumento!=null){
			cmbTipoDocumento.setSelectFirstOnUpdate(Boolean.TRUE);
			cmbTipoDocumento.setValue("");	
		}
	}
	public void setProperty(PaDocuAnexo docAnexo){
		setPaDocuAnexo(docAnexo);
	}
	
	public PaDocuAnexo getPaDocuAnexo() {
		return paDocuAnexo;
	}

	public void setPaDocuAnexo(PaDocuAnexo paDocuAnexo) {
		this.paDocuAnexo = paDocuAnexo;
	}
	public HtmlComboBox getCmbTipoDocumento() {
		return cmbTipoDocumento;
	}
	public void setCmbTipoDocumento(HtmlComboBox cmbTipoDocumento) {
		this.cmbTipoDocumento = cmbTipoDocumento;
	}
	public List<SelectItem> getLstTipoDocumento() {
		return lstTipoDocumento;
	}
	public void setLstTipoDocumento(List<SelectItem> lstTipoDocumento) {
		this.lstTipoDocumento = lstTipoDocumento;
	}
	public String getCmbValueTipoDocumento() {
		return cmbValueTipoDocumento;
	}
	public void setCmbValueTipoDocumento(String cmbValueTipoDocumento) {
		this.cmbValueTipoDocumento = cmbValueTipoDocumento;
	}
	public FileUpload getArchivo() {
		return archivo;
	}
	public void setArchivo(FileUpload archivo) {
		this.archivo = archivo;
	}
}
