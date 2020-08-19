package com.satc.pit.clientsample;

import com.satc.pit.*;

public class ClientSample {

	public static void main(String[] args) {
	        System.out.println("***********************");
	        System.out.println("Create Web Service Client...");
	        RecordInfraccionesService service1 = new RecordInfraccionesService();
	        System.out.println("Create Web Service...");
	        RecordInfractorRemote port1 = service1.getRecordInfraccionesPort();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port1.consultaVehiculo(null,null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.consultaConductor(null,null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        RecordInfractorRemote port2 = service1.getRecordInfraccionesPort();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port2.consultaVehiculo(null,null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port2.consultaConductor(null,null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("***********************");
	        System.out.println("Call Over!");
	}
}
