package com.sat.sisat.predial.managed;

import java.io.ByteArrayOutputStream;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.alfresco.content.AnexoDjPredial;
import com.sat.sisat.alfresco.content.Contenido;
import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.RjDocuAnexo;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.dto.DocumentoSustentatorioDTO;

@ManagedBean
@ViewScoped
public class DocumentoAnexoManaged extends BaseManaged {
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	VehicularBoRemote vehicularBo;
	
	private RjDocuAnexo rjDocuAnexo;
	
	private HtmlComboBox cmbTipoDocumento;
	private List<SelectItem> lstTipoDocumento=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnTipoDocumento=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnTipoDocumento=new HashMap<Integer,String>();
	private String cmbValueTipoDocumento;
	
	public DocumentoAnexoManaged(){
		
	}
	
	//Inicio ITANTAMANGO
	private FileUpload archivo;

	public FileUpload getArchivo() {
		return archivo;
	}
	public void setArchivo(FileUpload archivo) {
		this.archivo = archivo;
	}

	
	public void setProperty(FileUpload archivo){
		setArchivo(archivo);
		
	}
	//Fin ITANTAMANGO
	
	@PostConstruct
	public void init(){
		try{
			List<DocumentoSustentatorioDTO> lstGnTipoDocumento=vehicularBo.getAllRvDocumentoSustentatorio();
			Iterator<DocumentoSustentatorioDTO> it = lstGnTipoDocumento.iterator();  
			lstTipoDocumento = new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	DocumentoSustentatorioDTO obj = it.next();  
	        	lstTipoDocumento.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getDocSustentatorioId())));
	        	mapGnTipoDocumento.put(obj.getDescripcion(), obj.getDocSustentatorioId());
	        	mapIGnTipoDocumento.put(obj.getDocSustentatorioId(),obj.getDescripcion());
	        }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	public void guardar(){
		try{
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId==null||djId==Constante.RESULT_PENDING){
				RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
				registroPredio.inscripcionPredio();
			}
			djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId!=null){
				getRjDocuAnexo().setDjId(djId);
				//rjDocuAnexo.numeroDocumento
				//rjDocuAnexo.referencia
				getRjDocuAnexo().setTipoDocumentoId(mapGnTipoDocumento.get(cmbValueTipoDocumento));
				getRjDocuAnexo().setEstado(Constante.ESTADO_ACTIVO);
				//cc: getRjDocuAnexo().setUsuarioId(Constante.USUARIO_ID);
				//cc: getRjDocuAnexo().setFechaRegistro(DateUtil.getCurrentDate());
				//cc: getRjDocuAnexo().setTerminal(Constante.TERMINAL);
				
				//INICIO ITANTAMANGO
				if(archivo!=null&&archivo.getFile()!=null){
					getRjDocuAnexo().setReferencia(archivo.getFileName());
					getRjDocuAnexo().setContentId(archivo.getContentId());
					AnexoDjPredial dj=new AnexoDjPredial(archivo.getContentId()+archivo.getFileName(), Util.getBytesFromFile(archivo.getFile()), String.valueOf(archivo.getContentId()));
					RepositoryManager.guardarContenido(dj);
				}else{
					getRjDocuAnexo().setReferencia(null);
					getRjDocuAnexo().setContentId(null);	
				}
				//FIN ITANTAMANGO
				
				int result=registroPrediosBo.guardarRjDocuAnexo(getRjDocuAnexo());
				
				if(result>0){
					RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
					if(registroPredio!=null){
						registroPredio.loadDocumentosAnexos();
						limpiar();
					}
				}else{
					WebMessages.messageError("No existe DJ seleccionado");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
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
	public void delete(){
		try{
			if(rjDocuAnexo!=null){
				int result=registroPrediosBo.deleteRjDocuAnexo(rjDocuAnexo.getDjId(),rjDocuAnexo.getDocuAnexoId());
				if(result>0){
					RegistroPredioManaged registro = (RegistroPredioManaged) getManaged("registroPredioManaged");
					registro.loadDocumentosAnexos();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//INICIO ITANTAMANGO
	
	public void download(){
		try{
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
	        Contenido contenido =  RepositoryManager.buscarContenidoYMetaData(String.valueOf(rjDocuAnexo.getContentId()), output);
	         
		    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		    response.setContentType(Constantes.CONTENT_MIMETYPE_OCTET_STREAM);
		    response.addHeader("Content-Disposition","attachment;filename="+contenido.getName());
		    response.setContentLength(output.size());
		    ServletOutputStream servletOutputStream = response.getOutputStream();
		    servletOutputStream.write(output.toByteArray(), 0, output.size());
		    servletOutputStream.flush();
		    servletOutputStream.close();
		    FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//FIN ITANTAMANGO
	
	public void limpiar(){
		setRjDocuAnexo(new RjDocuAnexo());
		cmbTipoDocumento.setSelectFirstOnUpdate(Boolean.TRUE);
		cmbTipoDocumento.setValue("");
		archivo=new FileUpload();
	}
	public void setProperty(RjDocuAnexo docAnexo){
		setRjDocuAnexo(docAnexo);
	}
	public RjDocuAnexo getRjDocuAnexo() {
		return rjDocuAnexo;
	}
	public void setRjDocuAnexo(RjDocuAnexo rjDocuAnexo) {
		this.rjDocuAnexo = rjDocuAnexo;
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

}
