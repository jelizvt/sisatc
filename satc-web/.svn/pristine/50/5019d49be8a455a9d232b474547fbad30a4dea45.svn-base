
package pe.gob.sunarp.pide.controller;

import java.util.ArrayList;

public class PruebasSunarp {
	public static void main(String[] args) {
		 	
	        System.out.println("SUNARP");	        
	        
	        PideService service1 = new PideService();
	        PIDEWSService port1 = service1.getPIDEWSServicePort();
	        
	        
	        RespuestaPartidaBean asientos=new RespuestaPartidaBean(); 
	        
	        //port1.listarAsientos(zona, oficina, partida, registro)
	        
	        asientos=port1.listarAsientos("09","01","00028694","22000");
	        
	        //asientos.getListAsientos().get(0).getListPag().get(0).getNroPagRef();
	        
	        
	        System.out.println("Server said: "+asientos.listAsientos.get(1).getIdImgAsiento() );
	        
		
			
		
	        //Titularidad
	        /*
	        PideService service1 = new PideService();
	        PIDEWSService port2 = service1.getPIDEWSServicePort();
	        
	        ArrayList<ResultadoTitularidad> prueba=new ArrayList<ResultadoTitularidad>();
	        
	        RespuestaTitularidad  resultadoTitularidad=new RespuestaTitularidad ();
	        resultadoTitularidad=port2.buscarTitularidad("N", "ARRIBASPLATA", "BAZAN", "ALCIDES","");	        
	        
	        
	        for   (ResultadoTitularidad a : resultadoTitularidad.respuestaTitularidad)
	        {		
	        		prueba.add(a);
	        }
	        
	        
	        for (ResultadoTitularidad  a: prueba)
	        {
	        	System.out.println("CoRe: " +a.getNumeroDocumento() );
	        }
	        */
	        
	        
	        
	        
   }
	/*
	 private static RespuestaTitularidad consultarTitularidad() {		
		 	
		 	PideService service1 = new PideService();
		 	PIDEWSService port1 = service1.getPIDEWSServicePort();
		 	
		 	
		 	
		 	return port1.buscarTitularidad("N", "ASENJO", "VASQUEZ", "VICTOR", "");
	        
	    }
	 */

}
