package com.sat.sisat.predial.managed;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.richfaces.component.html.HtmlFileUpload;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.sat.sisat.alcabala.managed.SustentoAlcabalaManaged;
import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.alfresco.content.PapeletaInfraccion;
import com.sat.sisat.alfresco.util.Util;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.papeletas.managed.MedioProbatorioAnexoManaged;
import com.sat.sisat.vehicular.managed.NuevadjRegistroManaged;

@ManagedBean
@ViewScoped
public class FileUploadManaged extends BaseManaged {
	
	private FileUpload archivo;
	private HtmlFileUpload fileUpload;
	
	@EJB
	GeneralBoRemote general;
	
	public FileUploadManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		archivo=new FileUpload();
		fileUpload=new HtmlFileUpload();
		fileUpload.setMaxFilesQuantity(1);
	}
	public void guardar(){
		try{
			//enviar a gestor de contenidos
			Integer id=general.ObtenerCorrelativoTabla("af_contentmanager", 1);
			archivo.setContentId(id);
			String paramParentFileUpload =(String)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(),"paramParentFileUpload");
			if(paramParentFileUpload.equals("DA")){
				DocumentoAnexoManaged anexo = (DocumentoAnexoManaged) getManaged("documentoAnexoManaged");
				anexo.setProperty(archivo);
			}else if(paramParentFileUpload.equals("OI")){
				OtrasInstalacionesManaged instalacion = (OtrasInstalacionesManaged) getManaged("otrasInstalacionesManaged");
				instalacion.setProperty(archivo);
			}else if(paramParentFileUpload.equals("MP")){
				MedioProbatorioAnexoManaged medioprobatorio= (MedioProbatorioAnexoManaged) getManaged("medioProbatorioAnexoManaged");
				medioprobatorio.setProperty(archivo);
			}
			//INICIO IVO
			else if(paramParentFileUpload.equals("DV")){
				NuevadjRegistroManaged nuevadjRegistro=(NuevadjRegistroManaged) getManaged("nuevadjRegistroManaged");
				int idDocSus=nuevadjRegistro.getCurrentItem().getDocSustentatorioId();
				int indiceLista=0;
				for(int i=0;nuevadjRegistro.getLstDocSusten().size()>i;i++){
					if(nuevadjRegistro.getLstDocSusten().get(i).getDocSustentatorioId()==idDocSus){
						indiceLista=i;
					}
				}
				nuevadjRegistro.getLstDocSusten().get(indiceLista).setNomDocAdjunto(archivo.getFileName());
				nuevadjRegistro.getLstDocSusten().get(indiceLista).setContentId(archivo.getContentId());
				nuevadjRegistro.setProperty(archivo);
			}
			else if(paramParentFileUpload.equals("AL")){
				SustentoAlcabalaManaged sustentoAlcabala=(SustentoAlcabalaManaged)getManaged("sustentoAlcabalaManaged");
				int idDocSus=sustentoAlcabala.getCurrentItem().getDocSustentatorioId();
				int indiceLista=0;
				for(int i=0;sustentoAlcabala.getLstDocSusten().size()>i;i++){
					if(sustentoAlcabala.getLstDocSusten().get(i).getDocSustentatorioId()==idDocSus){
						indiceLista=i;
					}
				}
				
				sustentoAlcabala.getLstDocSusten().get(indiceLista).setNomDocAdjunto(archivo.getFileName());
				sustentoAlcabala.getLstDocSusten().get(indiceLista).setContentId(archivo.getContentId());
				sustentoAlcabala.setProperty(archivo);
			}
			//FIN IVO
			init();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void listener(UploadEvent event) throws Exception{  
        UploadItem item = event.getUploadItem();  
        archivo.setContentType(item.getContentType());
        archivo.setFile(item.getFile());
        archivo.setFileName(item.getFileName());
        archivo.setFileSize(item.getFileSize());
    }
	
	public void clear(){
		getFileUpload().setMaxFilesQuantity(1);
	}
	public FileUpload getArchivo() {
		return archivo;
	}
	public void setArchivo(FileUpload archivo) {
		this.archivo = archivo;
	}
	public HtmlFileUpload getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(HtmlFileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}
}
