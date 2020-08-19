package com.sat.sisat.coactivav2.managed;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.papeletas.managed.MedioProbatorioAnexoManaged;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persona.managed.RegistroPersonaManaged;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.managed.NuevadjRegistroManaged;

@ManagedBean
@ViewScoped
public class FileUploadRecManaged extends BaseManaged {
	
	private FileUpload archivo;
	private HtmlFileUpload fileUpload;
	
	@EJB
	GeneralBoRemote general;
	
	public FileUploadRecManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		clear();
	}
	public void clear(){
		archivo=new FileUpload();
		fileUpload=new HtmlFileUpload();
		fileUpload.setMaxFilesQuantity(1);
	}
	public void guardar(){
		try{
			Integer recId = (Integer) getSessionMap().get("selectedRecId");
			if(recId != null&&recId>0){
				CrudServiceBean connj = CrudServiceBean.getInstance();
				java.sql.Connection connection = connj.connectJasper();
				if(poseeImagen(connection, recId)){
					updateImageData(connection, recId);
					insertImageData(connection, recId);
				}else{
					insertImageData(connection, recId);
					GestionExpedienteManaged managed=(GestionExpedienteManaged)getManaged("gestionExpedienteManaged");
					managed.verEventos();
				}
			}
			clear();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void insertImageData(Connection conn, Integer recId) {
		int len;
		String query;
		PreparedStatement pstmt;
		try {
			File file = archivo.getFile();
			FileInputStream fis = new FileInputStream(file);
			len = (int) file.length();

			query = ("INSERT INTO dbo.co_rec_documento(rec_id,documento,fecha_registro,estado) VALUES(?,?,GETDATE(),1)");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, recId);
			// Method used to insert a stream of bytes
			pstmt.setBinaryStream(2, fis, len);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateImageData(Connection conn, Integer recId) {
		String query;
		PreparedStatement pstmt;

		try {
			query = ("UPDATE dbo.co_rec_documento SET estado=9,fecha_registro=GETDATE() WHERE rec_id=?");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, recId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean poseeImagen(Connection conn, Integer recId) {
		boolean exist = false;
		String query;
		PreparedStatement pstmt;
		try {
			query = (" SELECT count(*) as cantidad FROM dbo.co_rec_documento WHERE rec_id=? and estado=1");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, recId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("cantidad") > 0) {
					exist = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	public void listener(UploadEvent event) throws Exception{  
        UploadItem item = event.getUploadItem();  
        archivo.setContentType(item.getContentType());
        archivo.setFile(item.getFile());
        archivo.setFileName(item.getFileName());
        archivo.setFileSize(item.getFileSize());
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
