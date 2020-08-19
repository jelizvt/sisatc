package com.sat.sisat.papeletas;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.sat.sisat.papeletas.dto.ResolucionDTO;

public class Generator {

	public static Integer RESULT_FAILED=-1;
	public static Integer RESULT_PENDING=0;
	public static Integer RESULT_SUCCESS=1;
	
	public static void generate(ResolucionDTO resolucion,URL templatePath,String resolucionRootPath,String papeletaRootPath,URL logosatc_img) throws IOException,FileNotFoundException, DocumentException {
		PdfReader pdfTemplate = new PdfReader(templatePath);
		FileOutputStream fileOutputStream = new FileOutputStream(resolucionRootPath+File.separator+resolucion.getResolucionFileName());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfStamper stamper = new PdfStamper(pdfTemplate, fileOutputStream);
		stamper.setFormFlattening(true);
		stamper.getAcroFields().setField("txtResolucionId",resolucion.getResolucionId());
		stamper.getAcroFields().setField("txtNroResolucion",resolucion.getNroResolucion());
		stamper.getAcroFields().setField("txtPagina",resolucion.getPagina());
		stamper.getAcroFields().setField("txtFechaResolucion",resolucion.getFechaResolucion());
		stamper.getAcroFields().setField("txtCodigoPropietario",resolucion.getCodigoPropietario());
			stamper.getAcroFields().setField("txtApePropietario",resolucion.getApePropietario());
			stamper.getAcroFields().setField("txtNombrePropietario",resolucion.getNombrePropietario());
			stamper.getAcroFields().setField("txtDireccion1Propietario",resolucion.getDireccion1Propietario());
			stamper.getAcroFields().setField("txtDireccion2Propietario",resolucion.getDireccion2Propietario());
			stamper.getAcroFields().setField("txtApellidoInfractor",resolucion.getApellidoInfractor());
			stamper.getAcroFields().setField("txtNombreInfractor",resolucion.getNombreInfractor());
			stamper.getAcroFields().setField("txtDireccion1Infractor",resolucion.getDireccion1Infractor());
			stamper.getAcroFields().setField("txtDireccion2Infractor",resolucion.getDireccion2Infractor());
		stamper.getAcroFields().setField("txtInfraccion",resolucion.getInfraccion());
		stamper.getAcroFields().setField("txtMontoSancion",resolucion.getMontoSancion());
		stamper.getAcroFields().setField("txtEstadoSancion",resolucion.getEstadoSancion());
		stamper.getAcroFields().setField("txtNroLicencia",resolucion.getNroLicencia());
		stamper.getAcroFields().setField("txtPuntos",resolucion.getPuntos());
		stamper.getAcroFields().setField("txtNomApeResponsableSat",resolucion.getNomApeResponsableSat());
		stamper.getAcroFields().setField("txtCargoResponsableSat",resolucion.getCargoResponsableSat());
		stamper.getAcroFields().setField("txtNroResolucionInf",resolucion.getNroResolucion());
		stamper.getAcroFields().setField("txtNroPlacaInf",resolucion.getNroPlaca());
		stamper.getAcroFields().setField("txtNroPapeletaInf",resolucion.getNroPapeleta());
		
		//Papeleta
		PdfContentByte content = stamper.getOverContent(1);
		String fileName=papeletaRootPath+File.separator+resolucion.getPapeletaFileName();
		if(exist(fileName)){
			File file_img=new File(fileName);
			URL papeleta_img=file_img.toURI().toURL();
			if(papeleta_img!=null){
				Image image = Image.getInstance(papeleta_img);
		        image.setAbsolutePosition(262f, 412f);
		        image.scaleAbsolute(300, 340);
		        content.addImage(image);	
			}	
		}
		
        //Logo Sat Caj
        Image image2 = Image.getInstance(logosatc_img);
        image2.setAbsolutePosition(80f, 239f);
        image2.scalePercent(25);
        content.addImage(image2);
        
        //Logo Sat Caj - Firma
        Image image3 = Image.getInstance(logosatc_img);
        image3.setAbsolutePosition(280f, 320f);
        image3.scalePercent(25);
        content.addImage(image3);
        
		stamper.close();
		pdfTemplate.close();
	}
	
	public static Boolean exist(String filename){
		boolean exists=false;
		try{
			exists = (new File(filename)).exists();	
		}catch(Exception e){
			exists=false;
		}
		return exists;
	}
	
}
