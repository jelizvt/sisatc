package com.sat.sisat.notificaciones;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.alfresco.content.Notificacion;
import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;
import com.sat.sisat.notificaciones.dto.NotificacionesDTO;
import com.sat.sisat.papeletas.Loader;
import com.sat.sisat.papeletas.ParameterLoader;


public class LoadImageNotificacionesManager implements Runnable {
	private ArrayList<NotificacionesDTO> lNotificaciones=new ArrayList<NotificacionesDTO>();
	private String notificacionesRootPath;
	private String pathDes;
	
	public LoadImageNotificacionesManager(ArrayList<NotificacionesDTO> list,String pathDes){
		this.getlNotificaciones().addAll(list);
		this.pathDes=pathDes;
		init();
	}
	
	public LoadImageNotificacionesManager(){
		init();
	}
	
	public void init(){
		this.notificacionesRootPath=ParameterLoader.getParameter("directorio_notificaciones_digitalizados");
	}
	
	public void run(){
		try{
			Iterator<NotificacionesDTO> it=getlNotificaciones().iterator();
			while(it.hasNext()){
				NotificacionesDTO obj=it.next();
				File a = new File(notificacionesRootPath+obj.getNroDocumento()+Constantes.TIPO_ARCHIVO_JPG);
				System.out.println(a.getName());
				Notificacion pi=new Notificacion(obj.getContenId()+a.getName(),Util.getBytesFromFile(a),String.valueOf(obj.getContenId()));
	       		pi.setFechaNotificacion(obj.getFechaNotificacion());
	       		pi.setNotificadorId(obj.getNotificacionesId().toString());
	       		pi.setNroDocumento(obj.getNroDocumento());
				pi.setTipoActo(obj.getTipoActo());
				pi.setPersonaId(obj.getPersonaId().toString());
	       		RepositoryManager.guardarContenido(pi);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getFileNameNotificaciones(String notificacion){
		String fileName=this.notificacionesRootPath+File.separator+notificacion+".jpg";
		if(Loader.estadoImagenPapeleta(fileName)>0){
			return fileName;
		}
		return null;
	}

	public void copyfile(String srFile, String dtFile){
		Loader.copyfile(srFile, dtFile);
	}

	public ArrayList<NotificacionesDTO> getlNotificaciones() {
		return lNotificaciones;
	}

	public void setlNotificaciones(ArrayList<NotificacionesDTO> lNotificaciones) {
		this.lNotificaciones = lNotificaciones;
	}
	

	
}
