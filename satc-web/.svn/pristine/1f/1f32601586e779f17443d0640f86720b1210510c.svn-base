package com.sat.sisat.papeletas.managed;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.richfaces.component.html.HtmlInputText;

import com.sat.sisat.administracion.parameter.ParameterLoader;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.papeleta.dto.ResultadoCargaLoteDTO;
import com.sat.sisat.papeletas.LoadImagePapeletaManager;
import com.sat.sisat.papeletas.dto.PapeletaDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.PaCargaDetalleLote;
import com.sat.sisat.persistence.entity.PaCargaLote;

@ManagedBean
@ViewScoped
public class RegistroOficioLotesManaged extends BaseManaged {

	@EJB
	PapeletaBoRemote papeletaBo;
	
	private PaCargaLote cargaLote;
	private ArrayList<ResultadoCargaLoteDTO> records;
	private ResultadoCargaLoteDTO currentItem;
	
	private java.util.Date fechaOficio;
	private java.util.Date fechaRecepcion; 
	
	private Boolean pendienteCarga;
	
	private HtmlInputText txtarchivo;
	
	@PostConstruct
	public void init(){
		cargaLote=new PaCargaLote();
		cargaLote.setCargaLotesId(Constante.RESULT_PENDING);
		pendienteCarga=Boolean.FALSE;
	}
	
	public void RegistroOficioLotesManaged(){
		
	}

	public void guardar(){
		try{
			if(validaNumeroOficio()){
				cargaLote.setFecRecepcion(DateUtil.dateToSqlTimestamp(getFechaRecepcion()));
				cargaLote.setFecOficio(DateUtil.dateToSqlTimestamp(getFechaOficio()));
				cargaLote.setEstado(Constante.ESTADO_INACTIVO);
				cargaLote.setOrigen(Constante.ORIGEN_PAPELETA_MASIVO);
				//cc: cargaLote.setUsuarioId(Constante.USUARIO_ID);
				//cc: cargaLote.setFechaRegistro(DateUtil.getCurrentDate());
				//cc: cargaLote.setTerminal(Constante.TERMINAL);
				
				Integer Id=papeletaBo.guardarPaCargaLote(cargaLote);
				if(Id>Constante.RESULT_PENDING){
					cargaLote.setCargaLotesId(Id);
					pendienteCarga=Boolean.TRUE;
				}else{
					pendienteCarga=Boolean.FALSE;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Boolean validaNumeroOficio(){
		Boolean result=Boolean.FALSE;
		try{
			PaCargaLote carga=papeletaBo.getPaCargaLoteByNumeroOficio(cargaLote.getNumOficio(),cargaLote.getCargaLotesId()==null?Constante.RESULT_PENDING:cargaLote.getCargaLotesId());
			if(carga!=null){
				WebMessages.messageError("El numero de oficio : "+carga.getNumOficio()+" ya se encuentra registrado con fecha de recepci�n : "+DateUtil.convertDateToString(carga.getFecRecepcion()));
				result=Boolean.FALSE;
			}else{
				result=Boolean.TRUE;
			}
		}catch(Exception e){
			result=Boolean.FALSE;
		}
		return result;
	}
	public void procesarcarga(){
		try{
			records=new ArrayList<ResultadoCargaLoteDTO>();
			if(cargaLote.getCargaLotesId()>0){
				String directorio=ParameterLoader.getParameter("directorio_carga_papeletas");
				String nombreArchivo=(String)getTxtarchivo().getValue();
				if(nombreArchivo!=null&&nombreArchivo.trim().length()>0){
					Integer procesoCargaId=Constante.RESULT_PENDING;
					
					cargaLote.setArchivo(nombreArchivo);
					Integer cargaLotesId=papeletaBo.guardarPaCargaLote(cargaLote);
					cargaLote.setCargaLotesId(cargaLotesId);
					procesoCargaId=papeletaBo.iniciarProcesoCargaLote(directorio, nombreArchivo, cargaLote.getCargaLotesId());//call sp_carga_lotes_bulk
					if(procesoCargaId>0){
						records=papeletaBo.listaErrorCargaDetalleToles(cargaLote.getCargaLotesId(), procesoCargaId);
						if(records.size()>0){
							ResultadoCargaLoteDTO resultado=records.get(0);
							if(resultado.getCantidadError()==0){
								cargaLote.setEstado(Constante.ESTADO_ACTIVO);
								cargaLotesId=papeletaBo.guardarPaCargaLote(cargaLote);
								
								//--Carga masiva de imagenes de papeletas : INI
								ArrayList<PapeletaDTO> lista=papeletaBo.listarPapeletas(cargaLote.getCargaLotesId());
								//ArrayList<PapeletaDTO> lista=new ArrayList<PapeletaDTO>();
								ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
								String pathDes = ctx.getRealPath("/");
								
								com.sat.sisat.persistence.CrudServiceBean connj=CrudServiceBean.getInstance();
								LoadImagePapeletaManager task=new LoadImagePapeletaManager(lista,pathDes);
								task.run(connj.connectImage());
								papeletaBo.guardarPaDocuAnexo(lista);
								//--Carga masiva de imagenes de papeletas : FIN
							}else{
								cargaLote.setEstado(Constante.ESTADO_INACTIVO);
								cargaLotesId=papeletaBo.guardarPaCargaLote(cargaLote);
							}
						}
					}
				}else{
					WebMessages.messageError("Especifique el nombre del archivo de carga");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageError(e.getMessage());
		}
	}
	
	public void desacargaError(){
		// Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        BufferedInputStream input = null;
        BufferedOutputStream output = null;
		try{
			if(currentItem!=null){
				int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
		        
		        ArrayList<PaCargaDetalleLote> listaError=papeletaBo.getAllPaCargaDetalleLoteError(currentItem.getCargaLoteId());
		        
		        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				String pathDes = ctx.getRealPath("/");
		        
		        File excel=new File(pathDes+File.separator+"tmp"+File.separator+"detallelote.xls");
				WritableWorkbook xls = Workbook.createWorkbook(excel);
				xls.createSheet("Hoja", 0);
	       	 	WritableSheet sheet =xls.getSheet(0);
	    		writeDataSheet(sheet,listaError);
	    		xls.write();
	    		xls.close();
	    		
	    		 // Init servlet response.
	            response.reset();
	            /*response.setHeader("Content-Type", "application/vnd.ms-excel");
	            response.setHeader("Content-Length", String.valueOf(excel.length()));*/
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + "result.xls" + "\"");
	            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

	            // Open file.
	            input = new BufferedInputStream(new FileInputStream(excel), DEFAULT_BUFFER_SIZE);
	            // Write file contents to response.
	            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	            int length;
	            while ((length = input.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }

	            // Finalize task.
	            output.flush();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
            // Gently close streams.
            close(output);
            close(input);
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
	}
	private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it. It may be useful to 
                // know that this will generally only be thrown when the client aborted the download.
                e.printStackTrace();
            }
        }
    }
	
	private void writeDataSheet(WritableSheet s,ArrayList<PaCargaDetalleLote> listaError)throws WriteException
    {
	    WritableFont wf = new WritableFont(WritableFont.ARIAL,8);
	    WritableCellFormat cf = new WritableCellFormat(wf);
	    Iterator<PaCargaDetalleLote> it=listaError.iterator();
	    int i=0;
	    while(it.hasNext()){
	    	PaCargaDetalleLote err=it.next();
	    	Label l = new Label(1,i, String.valueOf(err.getCorrOficio()),cf);s.addCell(l);
	    	l = new Label(2,i,err.getNumPapeleta(),cf);s.addCell(l);
		    l = new Label(3,i,err.getConductor(),cf);s.addCell(l);
		    l = new Label(4,i,err.getNumLicencia(),cf);s.addCell(l);
		    l = new Label(5,i,err.getPlaca(),cf);s.addCell(l);
		    l = new Label(6,i,err.getCodInfraccion(),cf);s.addCell(l);
		    l = new Label(7,i,err.getFecPapeleta(),cf);s.addCell(l);
		    l = new Label(8,i,err.getNomPolicia(),cf);s.addCell(l);
		    l = new Label(9,i,err.getCipPolicia(),cf);s.addCell(l);
		    l = new Label(10,i,err.getErrorCode(),cf);s.addCell(l);
		    l = new Label(11,i,err.getErrorMessage(),cf);s.addCell(l);
		    
		    i++;
	    }
    }
	public void salir(){
		
	}

	public PaCargaLote getCargaLote() {
		return cargaLote;
	}

	public void setCargaLote(PaCargaLote cargaLote) {
		this.cargaLote = cargaLote;
	}

	public ArrayList<ResultadoCargaLoteDTO> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<ResultadoCargaLoteDTO> records) {
		this.records = records;
	}
	
	public java.util.Date getFechaOficio() {
		return fechaOficio;
	}

	public void setFechaOficio(java.util.Date fechaOficio) {
		this.fechaOficio = fechaOficio;
	}

	public java.util.Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(java.util.Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	public Boolean getPendienteCarga() {
		return pendienteCarga;
	}

	public void setPendienteCarga(Boolean pendienteCarga) {
		this.pendienteCarga = pendienteCarga;
	}
	public HtmlInputText getTxtarchivo() {
		return txtarchivo;
	}

	public void setTxtarchivo(HtmlInputText txtarchivo) {
		this.txtarchivo = txtarchivo;
	}
	
	public ResultadoCargaLoteDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(ResultadoCargaLoteDTO currentItem) {
		this.currentItem = currentItem;
	}

}
