package com.sat.sisat.papeletas;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;

import com.sat.sisat.papeletas.dto.PapeletaDTO;

public class LoadImagePapeletaManager implements Runnable {
	private ArrayList<PapeletaDTO> lPapeleta=new ArrayList<PapeletaDTO>();
	private String papeletaRootPath;
	private String pathDes;
	
	public LoadImagePapeletaManager(ArrayList<PapeletaDTO> list,String pathDes){
		this.lPapeleta.addAll(list);
		this.pathDes=pathDes;
		init();
	}
	
	public LoadImagePapeletaManager(){
		init();
	}
	
	public void init(){
		this.papeletaRootPath=ParameterLoader.getParameter("papeleta_root_path");
	}
	
	public void run(){
		
	}
	public void run(Connection conne){
		try{
			Iterator<PapeletaDTO> it=lPapeleta.iterator();
			while(it.hasNext()){
				PapeletaDTO obj=it.next();
				String fileName=getFileNamePapeleta(obj.getNroPapeleta());
				if(fileName!=null){
					insertfile(conne,fileName, obj.getPapeletaId());
					obj.setEstado(Loader.RESULT_SUCCESS);
					obj.setFileName("\\satc\\tmp\\"+obj.getNroPapeleta()+".jpg");
				}else{
					obj.setEstado(Loader.RESULT_FAILED);
					obj.setFileName(null);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getFileNamePapeleta(String papeleta){
		String fileName=this.papeletaRootPath+File.separator+papeleta+".jpg";
		if(Loader.estadoImagenPapeleta(fileName)>0){
			return fileName;
		}
		return null;
	}

	public void copyfile(String srFile, String dtFile){
		Loader.copyfile(srFile, dtFile);
	}
	
	public void insertfile(Connection conne,String srFile,Integer papeletaId){
		java.sql.Connection connection=null;
        try {
			insertImageData(conne, srFile, papeletaId);
	     }catch (Exception jre) {
	    	 jre.printStackTrace();
	     }
    }
	
	public void insertImageData(Connection conn,String img,Integer papeletaId)
    {
        int len;
        String query;
        PreparedStatement pstmt;
        
        try
        {
            File file = new File(img);
            FileInputStream fis = new FileInputStream(file);
            len = (int)file.length();

            query = ("INSERT INTO bd_imagenes.dbo.t_papeleta_imagenes(papeleta_id,imapap,fecima,fecreg,perarereg) VALUES(?,?,GETDATE(),GETDATE(),1)");
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,papeletaId);
            // Method used to insert a stream of bytes
            pstmt.setBinaryStream(2, fis, len); 
            pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
   }
	
	public ArrayList<PapeletaDTO> getlPapeleta() {
		return lPapeleta;
	}

	public void setlPapeleta(ArrayList<PapeletaDTO> lPapeleta) {
		this.lPapeleta = lPapeleta;
	}
	
}
