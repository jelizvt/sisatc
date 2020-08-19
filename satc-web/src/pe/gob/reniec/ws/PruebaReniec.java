package pe.gob.reniec.ws;

import java.util.Arrays;
import java.util.Scanner;

public class PruebaReniec {
	
	
	
		public static void main(String[] args) {
		        
				 String DniConsulta = "", RucUsuario = "20453807267", DniUsuario = "42555584", Clave = "42555584";
			        System.out.println("Ingrese Nro DNI a Consultar: ");
			        Scanner entradaDni = new Scanner(System.in);
			        DniConsulta = entradaDni.nextLine();
			        
			        PeticionConsulta arg0 = new PeticionConsulta();
			        arg0.setNuDniConsulta(DniConsulta);
			        arg0.setNuDniUsuario(DniUsuario);
			        arg0.setNuRucUsuario(RucUsuario);
			        arg0.setPassword(Clave);
			        
			        
			        ResultadoConsulta respuesta=new ResultadoConsulta ();
			        
			        respuesta =consultar(arg0);
			        
			        
			        System.out.println("CoRe: " + respuesta.coResultado);
			        System.out.println("CoRe: " + respuesta.datosPersona.apPrimer);
			        System.out.println("CoRe: " + respuesta.datosPersona.apSegundo);
			        System.out.println("CoRe: " + respuesta.datosPersona.prenombres);
			        System.out.println("CoRe: " + respuesta.datosPersona.direccion);
			        System.out.println("CoRe: " + respuesta.datosPersona.estadoCivil);
			        System.out.println("CoRe: " + respuesta.datosPersona.restriccion);
			        System.out.println("Foto: " + Arrays.toString(respuesta.datosPersona.foto));
			        
			        
			        /*
			        System.out.println("CoRe: " + consultar(arg0).coResultado);
			        System.out.println("Apellido Paterno: " + consultar(arg0).datosPersona.apPrimer);
			        System.out.println("Apellido Materno: " + consultar(arg0).datosPersona.apSegundo);
			        System.out.println("Nombres: " + consultar(arg0).datosPersona.prenombres);
			        System.out.println("Ubigeo: " + consultar(arg0).datosPersona.ubigeo);  
			        System.out.println("Direccion: " + consultar(arg0).datosPersona.direccion);  
			        System.out.println("Estado Civil: " + consultar(arg0).datosPersona.estadoCivil);
			        System.out.println("Restricion: " + consultar(arg0).datosPersona.restriccion);
			        System.out.println("Foto: " + Arrays.toString(consultar(arg0).datosPersona.foto));
			        System.out.println("Resultado SATCaj: " + consultar(arg0).deResultado);
			        */
			                
		    }
			 private static ResultadoConsulta consultar(PeticionConsulta arg0) {
			        ReniecConsultaDni service = new ReniecConsultaDni();
			        ReniecConsultaDniPortType port = service.getReniecConsultaDniHttpsSoap11Endpoint();
			        return port.consultar(arg0);
			    }
			 
			 

}
