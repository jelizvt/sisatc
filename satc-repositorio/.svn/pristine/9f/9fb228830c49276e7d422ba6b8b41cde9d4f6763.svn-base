package com.sat.sisat.test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLAddAspect;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class Test {

	/**
	 * @param args
	 * @throws AuthenticationFault
	 */
	public static void main(String[] args)  {
		
		try {
			
			AuthenticationUtils.startSession("admin", "adminadmin");
		

			Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");
			ParentReference companyHomeParent = new ParentReference(storeRef,
					null, "/app:company_home/cm:prueba", Constants.ASSOC_CONTAINS,
					null);

			// Generacion del nombre que aparecera en el repositorio

			String name = "Nombre_" + System.currentTimeMillis();
			companyHomeParent.setChildName("cm:" + name);

			NamedValue[] contentProps = new NamedValue[2];
			contentProps[0] = Utils.createNamedValue(Constants.PROP_NAME, name);
			contentProps[1] = Utils.createNamedValue(Constants.PROP_TITLE, "titulo de la Prueba!!!!!");
			
			System.out.println("TYPE_CONTENT: "+Constants.TYPE_CONTENT);
			
			
			CMLCreate create = new CMLCreate("1", companyHomeParent, null,
					null, null, "{http://www.alfresco.org/model/webservicetestmodel/1.0}testproperties", contentProps);					
					//null, null, "{http://www.infoaxon.com/book/models/catalogue/1.0}resDeterminacion", contentProps);	
					//null, null, Constantes.TYPE_PRUEBA, contentProps);	
			
			
//			System.out.println("Prop_Title: "+Constants.PROP_TITLE);
//			System.out.println("Prop_Description "+Constants.PROP_DESCRIPTION);
			
			Date fecha = Calendar.getInstance().getTime();
			String formatDateTime = "yyyy-MM-dd'T'HH:mm:ss.mmmZ";
			String str = null;
			
			try {
			
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(formatDateTime, Locale.US);
			str = sdf.format(fecha);
			
			int length = str.length();
			
			str = str.substring(0, length-2)+":"+str.substring(length-2);
			
			
			
			
			
			
			System.out.println(str);
			
			Date test = Util.convertStringISO8601ToDateTime(str);
			
			System.out.println(test.toString());
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
			NamedValue[] titledProps = new NamedValue[]{
//            Utils.createNamedValue(Constants.createQNameString("http://www.infoaxon.com/book/models/catalogue/1.0", "nroRD"), "062-019-01020121"),
//            Utils.createNamedValue(Constants.createQNameString("http://www.infoaxon.com/book/models/catalogue/1.0", "codContribRD"), "CODCONTRIB00101"),
//            Utils.createNamedValue(Constants.createQNameString("http://www.infoaxon.com/book/models/catalogue/1.0", "fechaRD"), str)
            
            
            Utils.createNamedValue(Constants.createQNameString("http://www.alfresco.org/model/webservicetestmodel/1.0", "datetimeProp"), str)
			 
			
			};
			 
            CMLAddAspect addAspect = new CMLAddAspect(Constants.ASPECT_TITLED, titledProps, null, "1");
            
            
            CML cml = new CML();
            cml.setCreate(new CMLCreate[] {create});
            
            cml.setAddAspect(new CMLAddAspect[] {addAspect});
            
            
            UpdateResult[] result = WebServiceFactory.getRepositoryService().update(cml);     
            Reference content = result[0].getDestination();
            
            
            ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
            String text = "The quick brown fox jumps over the lazy dog";
            ContentFormat contentFormat = new ContentFormat("text/plain", "UTF-8");
            
            System.out.println("PROP_CONTENT: "+ Constants.PROP_CONTENT);	
            Content contentRef = contentService.write(content, Constants.PROP_CONTENT, text.getBytes(), contentFormat);
            System.out.println("Content Length: " + contentRef.getLength());
            
            

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			// End the session
			AuthenticationUtils.endSession();			
			System.exit(0);
		}

	}

}
