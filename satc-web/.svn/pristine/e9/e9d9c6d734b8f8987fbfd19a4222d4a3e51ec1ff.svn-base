package com.sat.sisat.alcabala.managed;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.sat.sisat.alcabala.business.AlcabalaRemote;
import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.alfresco.content.AnexoAlcabala;
import com.sat.sisat.alfresco.content.AnexoDjVehicular;
import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.RaAlcabalaSustento;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.dto.DocumentoSustentatorioDTO;

import edu.umn.gis.mapscript.intarray;

@ManagedBean
@ViewScoped
public class SustentoAlcabalaManaged extends BaseManaged {

	private static final long serialVersionUID = 1L;

	@EJB
	VehicularBoRemote vehicularBo;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	AlcabalaRemote alcabalaBo;

	private AnexosDeclaVehicDTO anexosDecla = new AnexosDeclaVehicDTO();
	
	public AnexosDeclaVehicDTO getAnexosDecla() {
		return anexosDecla;
	}

	public void setAnexosDecla(AnexosDeclaVehicDTO anexosDecla) {
		this.anexosDecla = anexosDecla;
	}



	private ArrayList<FileUpload> lstArchivo = new ArrayList<FileUpload>();
	
	public ArrayList<FileUpload> getLstArchivo() {
		return lstArchivo;
	}

	public void setLstArchivo(ArrayList<FileUpload> lstArchivo) {
		this.lstArchivo = lstArchivo;
	}



	private FileUpload archivo;
	
	public FileUpload getArchivo() {
		return archivo;
	}

	public void setArchivo(FileUpload archivo) {
		this.archivo = archivo;
	}
	
	public void setProperty(FileUpload archivo){
		lstArchivo.add(archivo);
		//setArchivo(archivo);
		//setLstArchivo(lstArchivo)
	}
	
	
	public void setProperty(List<AnexosDeclaVehicDTO> lstTransferentes){
		setLstAnexosMuestra(lstTransferentes);
	}
	
	private List<DocumentoSustentatorioDTO> lstDocSusten = new ArrayList<DocumentoSustentatorioDTO>();
	private List<RaAlcabalaSustento> lstAnexos = new ArrayList<RaAlcabalaSustento>();
	private List<AnexosDeclaVehicDTO> lstAnexosMuestra = new ArrayList<AnexosDeclaVehicDTO>();
	
	public List<AnexosDeclaVehicDTO> getLstAnexosMuestra() {
		return lstAnexosMuestra;
	}

	public void setLstAnexosMuestra(List<AnexosDeclaVehicDTO> lstAnexosMuestra) {
		this.lstAnexosMuestra = lstAnexosMuestra;
	}
	
	private DocumentoSustentatorioDTO currentItem;

	public DocumentoSustentatorioDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(DocumentoSustentatorioDTO currentItem) {
		this.currentItem = currentItem;
	}

	@PostConstruct
	public void init() {
		try {
			lstDocSusten = vehicularBo.getAllRvDocumentoSustentatorio();

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
	}


	public CalculoAlcabalaManaged getCalculoAlcabalaManaged() {
		return (CalculoAlcabalaManaged) getManaged("calculoAlcabalaManaged");
	}
	
	public void setParameters(){
		String paramParentFileUpload = FacesUtil.getRequestParameter("paramParentFileUpload");
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(),"paramParentFileUpload",paramParentFileUpload);
	}
	
	
	public void download(){
		try{
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
	        RepositoryManager.buscarContenido(String.valueOf(anexosDecla.getContentId()), output);
	         
		    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		    response.setContentType(Constantes.CONTENT_MIMETYPE_JPEG);
		    response.addHeader("Content-Disposition","attachment;filename="+anexosDecla.getNomDocAdjunto());
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

	public void agregarAnexos(ActionEvent ev) {


		try {

			for (DocumentoSustentatorioDTO dsd : lstDocSusten) {
				
				if (dsd.isSelected()) {
					//INICIO ITANTAMANGO
					lstAnexosMuestra.add(new AnexosDeclaVehicDTO(dsd.getDocSustentatorioId(), dsd.getDescripcion(), dsd.getGlosa(), dsd.getNomDocAdjunto(),dsd.getContentId()));
					
					if(getLstArchivo()!=null){
						
						for(int i=0;i<lstArchivo.size();i++){
							if(lstArchivo.get(i).getContentId()==dsd.getContentId()){
								AnexoAlcabala aAl=new AnexoAlcabala(lstArchivo.get(i).getContentId()+lstArchivo.get(i).getFileName(),Util.getBytesFromFile(lstArchivo.get(i).getFile()),String.valueOf(lstArchivo.get(i).getContentId()));
								RepositoryManager.guardarContenido(aAl);
							}
						}
					}
					//FIN ITANTAMANGO
				}
				
			}
			getCalculoAlcabalaManaged().setLstAnexosMuestra(lstAnexosMuestra);
			init();

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
	}
	
	
	public void eliminarAnexoDeLista(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				AnexosDeclaVehicDTO adv = (AnexosDeclaVehicDTO) uiData.getRowData();
				lstAnexosMuestra.remove(adv);
				
				getCalculoAlcabalaManaged().getLstAnexosMuestra().remove(adv);

			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
	}
	

	public List<RaAlcabalaSustento> getLstAnexos() {
		return lstAnexos;
	}

	public void setLstAnexos(List<RaAlcabalaSustento> lstAnexos) {
		this.lstAnexos = lstAnexos;
	}

	public void guardarDocumentos() {

	}

	public List<DocumentoSustentatorioDTO> getLstDocSusten() {
		return lstDocSusten;
	}

	public void setLstDocSusten(List<DocumentoSustentatorioDTO> lstDocSusten) {
		this.lstDocSusten = lstDocSusten;
	}
		
}
