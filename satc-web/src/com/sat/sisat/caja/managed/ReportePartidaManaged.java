package com.sat.sisat.caja.managed;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjPartidaEntity;
import com.sat.sisat.caja.dto.CjReciboEntity;
import com.sat.sisat.caja.dto.CjReciboPagoEntity;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;

@ManagedBean
@ViewScoped
public class ReportePartidaManaged extends BaseManaged  {


	@EJB
	CajaBoRemote cajaBo;

	//declaracion de variables 
	private List<CjPartidaEntity> lstReporte= new ArrayList<CjPartidaEntity>();
	private ArrayList<CjPartidaEntity> lstPartida = new ArrayList<CjPartidaEntity>();
	
	private ArrayList<CjPartidaEntity> records;

	private Date fecha_Inicio;
	private Date fecha_Fin;
	
	private int selectedOptBusc = 1;
	
	
	
	public ReportePartidaManaged() {
		getSessionManaged().setLinkRegresar("/sisat/caja/ReportePartida.xhtml");	
	}

	@PostConstruct
	public void init() {
		
	}
	
	
	public void buscar(){
		try {
			
			//int opcion = getSelectedOptBusc();
			
			int cajero_id=getSessionManaged().getUsuarioLogIn().getUsuarioId();
		
		    records = cajaBo.ObtenerReportePartidaDiaria(cajero_id,fecha_Inicio, fecha_Fin);
		    
			//setRecords(records);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();  
			addFatalMessage(e.getMessage());
		}
		
		
	}
	
	
	public String salir() {
		return sendRedirectPrincipal();
	}
	
	public List<CjPartidaEntity> getLstReporte() {
		return lstReporte;
	}

	public void setLstReporte(List<CjPartidaEntity> lstReporte) {
		this.lstReporte = lstReporte;
	}

	public ArrayList<CjPartidaEntity> getLstPartida() {
		return lstPartida;
	}

	public void setLstPartida(ArrayList<CjPartidaEntity> lstPartida) {
		this.lstPartida = lstPartida;
	}
	public ArrayList<CjPartidaEntity> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<CjPartidaEntity> records) {
		this.records = records;
	}

	public int getSelectedOptBusc() {
		return selectedOptBusc;
	}

	public void setSelectedOptBusc(int selectedOptBusc) {
		this.selectedOptBusc = selectedOptBusc;
	}

	public Date getFecha_Inicio() {
		return fecha_Inicio;
	}

	public void setFecha_Inicio(Date fecha_Inicio) {
		this.fecha_Inicio = fecha_Inicio;
	}

	public Date getFecha_Fin() {
		return fecha_Fin;
	}

	public void setFecha_Fin(Date fecha_Fin) {

		Calendar c = Calendar.getInstance();
		c.setTime(fecha_Fin);
		// le añadimos 1 dia para que realize la busqueda x < (dia/mes/2013 00:00:00)
		c.add(Calendar.DATE, 1);

		this.fecha_Fin = c.getTime();
	}
}

