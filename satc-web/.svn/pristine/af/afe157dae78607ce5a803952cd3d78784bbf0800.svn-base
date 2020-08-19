package com.sat.sisat.predial.managed;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.component.html.HtmlFileUpload;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.persistence.entity.RjDocumentoSustentatorio;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;

@ManagedBean
@ViewScoped
public class DocumentoSustentoManaged {
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	private String numeroDocumento;
	private java.util.Date fechaDocumento;
	private java.util.Date fechaInical;
	private java.util.Date fechaFinal;
	private File file;
	private String fileName;

	private RjDocumentoSustentatorio sustento;
	
	private ArrayList<RjDocumentoSustentatorio> records;
	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private int currentRow;
	private RjDocumentoSustentatorio currentItem = new RjDocumentoSustentatorio();
	
	//1 especial si / no
	//2 habitable / No habitable
	private String tipoDocumentoSustento;
	
	private HtmlFileUpload fileUpload;
	
	public HtmlFileUpload getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(HtmlFileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}
	public DocumentoSustentoManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		sustento=new RjDocumentoSustentatorio();
		records=new ArrayList<RjDocumentoSustentatorio>();
		fileUpload=new HtmlFileUpload();
		fileUpload.setMaxFilesQuantity(1);
		
		Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
		if(djId!=null){
			poblado(djId,getInteger(getTipoDocumentoSustento()));	
		}
	}
	public void guardar(){
		
	}
	
	public void clear(){
		getFileUpload().setMaxFilesQuantity(1);
	}

	public void limpiar(){
		setNumeroDocumento("");
		setFechaDocumento(null);
		setFechaInical(null);
		setFechaFinal(null);
		sustento=new RjDocumentoSustentatorio();
		clear();
	}
	
	public int getInteger(String value){
		int result=0;
		try{
			if(value!=null)
				result=Integer.valueOf(value);
		}catch(Exception e){
			e.printStackTrace();
			result=-1;
		}
		return result;
	}
	public void poblado(Integer dj_id,Integer tipo_docu_sustento_id){
		
	}
	
	public void listener(UploadEvent event) throws Exception{  
        UploadItem item = event.getUploadItem();  
        setFileName(item.getFileName());
        setFile(item.getFile());
    }
	
	private static final java.sql.Timestamp DateToSqlTimestamp(java.util.Date utilDate) {
		return new java.sql.Timestamp(utilDate.getTime());
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public java.util.Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(java.util.Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public java.util.Date getFechaInical() {
		return fechaInical;
	}

	public void setFechaInical(java.util.Date fechaInical) {
		this.fechaInical = fechaInical;
	}

	public java.util.Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(java.util.Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public RjDocumentoSustentatorio getSustento() {
		return sustento;
	}

	public void setSustento(RjDocumentoSustentatorio sustento) {
		this.sustento = sustento;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public ArrayList<RjDocumentoSustentatorio> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<RjDocumentoSustentatorio> records) {
		this.records = records;
	}

	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public HtmlExtendedDataTable getTable() {
		return table;
	}

	public void setTable(HtmlExtendedDataTable table) {
		this.table = table;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public RjDocumentoSustentatorio getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(RjDocumentoSustentatorio currentItem) {
		this.currentItem = currentItem;
	}

	public String getTipoDocumentoSustento() {
		return tipoDocumentoSustento;
	}

	public void setTipoDocumentoSustento(String tipoDocumentoSustento) {
		this.tipoDocumentoSustento = tipoDocumentoSustento;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
