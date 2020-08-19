package com.sat.sisat.papeletas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Loader {

	public static Integer RESULT_FAILED=-1;
	public static Integer RESULT_PENDING=0;
	public static Integer RESULT_SUCCESS=1;
	
	public static Boolean exist(String filename){
		boolean exists=false;
		try{
			exists = (new File(filename)).exists();	
		}catch(Exception e){
			exists=false;
		}
		return exists;
	}
	
	public static Integer estadoImagenPapeleta(String fileName){
		Integer result=RESULT_PENDING;
		if(exist(fileName)){
			File file_img=new File(fileName);
			if(file_img.length()>0){
				result=RESULT_SUCCESS;	
			}else{
				result=RESULT_FAILED;
			}
		}else{
			result=RESULT_FAILED;
		}
		return result;
	}
	
	 public static void copyfile(String srFile, String dtFile){
		  try{
			  File f1 = new File(srFile);
			  File f2 = new File(dtFile);
			  InputStream in = new FileInputStream(f1);
			  
			  //For Append the file.
			//  OutputStream out = new FileOutputStream(f2,true);
	
			  //For Overwrite the file.
			  OutputStream out = new FileOutputStream(f2);
	
			  byte[] buf = new byte[1024];
			  int len;
			  while ((len = in.read(buf)) > 0){
				  out.write(buf, 0, len);
			  }
			  in.close();
			  out.close();
			  System.out.println("File copied.");
		  }catch(FileNotFoundException ex){
			  System.out.println(ex.getMessage() + " in the specified directory.");
			  System.exit(0);
		  }catch(IOException e){
			  System.out.println(e.getMessage());  
		  }
	}
}
