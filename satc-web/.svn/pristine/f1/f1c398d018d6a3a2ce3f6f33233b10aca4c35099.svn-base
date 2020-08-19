package pe.gob.reniec.ws.clientsample;

import pe.gob.reniec.ws.*;

public class ClientSample {

	public static void main(String[] args) {
	        System.out.println("***********************");
	        System.out.println("Create Web Service Client...");
	        ReniecConsultaDni service1 = new ReniecConsultaDni();
	        System.out.println("Create Web Service...");
	        ReniecConsultaDniPortType port1 = service1.getReniecConsultaDniHttpsSoap11Endpoint();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port1.consultar(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.actualizarcredencial(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        ReniecConsultaDniPortType port2 = service1.getReniecConsultaDniHttpsSoap11Endpoint();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port2.consultar(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port2.actualizarcredencial(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("***********************");
	        System.out.println("Call Over!");
	}
}
