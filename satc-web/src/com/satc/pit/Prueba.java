package com.satc.pit;

import java.util.List;

public class Prueba {

	public static void main(String[] args) {
		
		
		//Consumimos el servicio WEB
		RecordInfraccionesService service1 = new RecordInfraccionesService();
		RecordInfractorRemote port2 = service1.getRecordInfraccionesPort();
		
		
		//DTO para REcord de Vehículo
		List<RecordVehiculo> rv;
		rv=port2.consultaVehiculo("mpcconsultas","Muni#ws@2018","73896m");
		
		for (int i = 0; i < rv.size(); i++) {
			System.out.println(rv.get(i).getNroPapeleta() +" Mensaje: "+rv.get(i).getMensaje() );			
		}
		
		
		//DTO para REcord de Conductor
		List<RecordConductor> rc;
		rc=port2.consultaConductor("mpcconsultas","Muni#ws@2018","26628189");

		
		for (int i = 0; i < rc.size(); i++) {
			System.out.println(rc.get(i).getNroPapeleta() +" Mensaje: "+rc.get(i).getMensaje() );			
		}

	}

}
