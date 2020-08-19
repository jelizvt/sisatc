package com.sat.sisat.coactivav2.managed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.CarteraExigibilidad;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.cobranzacoactiva.dto.ValorCartera;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;

@ManagedBean
@ViewScoped
public class AcumulaCarteraExigibilidadManaged extends BaseManaged {
	/**
	 * fox
	 */
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private List<InformeTransferidoDetalle> valoresCartera = new ArrayList<InformeTransferidoDetalle>();
	private List<InformeTransferidoDetalle> valoresContrib = new ArrayList<InformeTransferidoDetalle>();
	
	private CarteraExigibilidad carteraExigibilidad = new CarteraExigibilidad();
	private InformeTransferidoDetalle selValorAcumula=new InformeTransferidoDetalle(); 

	private Boolean selTodos = Boolean.FALSE;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	 	private boolean permisoAcumularTodo;	 	
	 	private boolean permisoDesacumularTodo;
	 	private boolean permisoAcumular;
	 	private boolean permisoRetirar;
	 	private boolean permisoGenerar;

	 // FIN PERMISOS PARA EL MODULO
		 	
	@PostConstruct
	public void init() {
		permisosMenu();
		try {
			
			CarteraExigibilidad selCartera = (CarteraExigibilidad) getSessionMap().get("carteraExigibilidad");
			if(selCartera != null){
				setCarteraExigibilidad(selCartera);
				consultarValoresCartera(selCartera.getCarteraId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId=0;
			if(getSessionManaged().getMateriaId() == 1) {
				submenuId = Constante.CARTERA_VALORES_TRIB;
			}else if (getSessionManaged().getMateriaId() == 2) {
				submenuId = Constante.CARTERA_VALORES_NO_TRIB;
			}
		 	
			int permisoAcumularTodoId = Constante.ACUMULAR_TODO;
			int permisoDesacumularTodoId = Constante.DESACUMULAR_TODO;
			int permisoAcumularId = Constante.ACUMULAR;			
			int permisoRetirarId = Constante.RETIRAR;
			int permisoGenerarID = Constante.GENENRAR;
			
			permisoAcumularTodo = false;	 	
		 	permisoDesacumularTodo = false;
		 	permisoAcumular = false;
		 	permisoRetirar = false;
		 	permisoGenerar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoAcumularTodoId) {
					permisoAcumularTodo = true;
				}
				if(lsm.getItemId() == permisoDesacumularTodoId) {
					permisoDesacumularTodo = true;
				}
				if(lsm.getItemId() == permisoAcumularId) {
					permisoAcumular = true;
				}
				if(lsm.getItemId() == permisoRetirarId) {
					permisoRetirar = true;
				}
				if(lsm.getItemId() == permisoGenerarID) {
					permisoGenerar = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	public void valoresContribuyente() {
		try{
			valoresContrib=cobranzaCoactivaBo.consultarValoresCartera(carteraExigibilidad.getCarteraId(), selValorAcumula.getPersonaId());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void desAcumulaContribuyente(){
		try{
			cobranzaCoactivaBo.registraDesAcumulacion(carteraExigibilidad.getCarteraId(),selValorAcumula.getPersonaId(),getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
			consultarValoresCartera(carteraExigibilidad.getCarteraId());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void retirarValor(){
		try{
			cobranzaCoactivaBo.retiraValorCartera(carteraExigibilidad.getCarteraId(),selValorAcumula.getActoId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			consultarValoresCartera(carteraExigibilidad.getCarteraId());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void acumularTodo() {
		try {
			cobranzaCoactivaBo.registraAcumulacionTodo(carteraExigibilidad.getCarteraId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			consultarValoresCartera(carteraExigibilidad.getCarteraId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void desAcumularTodo(){
		try {
			cobranzaCoactivaBo.registraDesAcumulacionTodo(carteraExigibilidad.getCarteraId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			consultarValoresCartera(carteraExigibilidad.getCarteraId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void consultarValoresCartera(Integer carteraId) {
		try {
			valoresCartera=cobranzaCoactivaBo.consultarValoresCartera(carteraId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void valueChangeListenerSelTodos(ValueChangeEvent ev){
		String nv = ev.getNewValue().toString();
		if(nv.equals("true")){
			for(InformeTransferidoDetalle detalle:valoresContrib){
				detalle.setSelected(Boolean.TRUE);				
			}
		}else{
			for(InformeTransferidoDetalle detalle:valoresContrib){
				detalle.setSelected(Boolean.FALSE);				
			}
		}
	}
	
	public void acumularValores(){
		try {
			StringBuffer listDetalleId=new StringBuffer();
			for(InformeTransferidoDetalle detalle : valoresContrib){
				if(detalle.isSelected()){
					listDetalleId.append(detalle.getProspectoId()).append(",");
				}
			}
			if(listDetalleId.toString().length()>0){
				cobranzaCoactivaBo.registraAcumulacion(listDetalleId.toString(), carteraExigibilidad.getCarteraId(), selValorAcumula.getPersonaId(), getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
				consultarValoresCartera(carteraExigibilidad.getCarteraId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registraExpediente(){
		try{
			cobranzaCoactivaBo.registraExpedientes(carteraExigibilidad.getCarteraId(), getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void descargarExcel() {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		
		row1.createCell(0).setCellValue("item");
		row1.createCell(1).setCellValue("Tipo de valor");
		row1.createCell(2).setCellValue("Nro de valor");
		row1.createCell(3).setCellValue("Código");
		row1.createCell(4).setCellValue("Contribuyente");
		row1.createCell(5).setCellValue("Periodo");
		row1.createCell(6).setCellValue("Concepto");
		row1.createCell(7).setCellValue("SubConcepto");
		row1.createCell(8).setCellValue("Deuda");
		row1.createCell(9).setCellValue("Situacion");

		for (InformeTransferidoDetalle data : valoresCartera) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			row.createCell(columnIndex++).setCellValue(data.getNroValor());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresPersona());
			row.createCell(columnIndex++).setCellValue(data.getPeriodo());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getSubConcepto());
			row.createCell(columnIndex++).setCellValue(data.getMontoDeuda());
			row.createCell(columnIndex++).setCellValue(data.getSituacion());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=acumula_cartera_exigibilidad.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	public List<InformeTransferidoDetalle> getValoresContrib() {
		return valoresContrib;
	}

	public void setValoresContrib(List<InformeTransferidoDetalle> valoresContrib) {
		this.valoresContrib = valoresContrib;
	}
	
	public List<InformeTransferidoDetalle> getValoresCartera() {
		return valoresCartera;
	}

	public void setValoresCartera(List<InformeTransferidoDetalle> valoresCartera) {
		this.valoresCartera = valoresCartera;
	}

	public CarteraExigibilidad getCarteraExigibilidad() {
		return carteraExigibilidad;
	}

	public void setCarteraExigibilidad(CarteraExigibilidad carteraExigibilidad) {
		this.carteraExigibilidad = carteraExigibilidad;
	}
	
	public InformeTransferidoDetalle getSelValorAcumula() {
		return selValorAcumula;
	}

	public void setSelValorAcumula(InformeTransferidoDetalle selValorAcumula) {
		this.selValorAcumula = selValorAcumula;
	}
	public Boolean getSelTodos() {
		return selTodos;
	}

	public void setSelTodos(Boolean selTodos) {
		this.selTodos = selTodos;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoAcumularTodo() {
		return permisoAcumularTodo;
	}

	public void setPermisoAcumularTodo(boolean permisoAcumularTodo) {
		this.permisoAcumularTodo = permisoAcumularTodo;
	}

	public boolean isPermisoDesacumularTodo() {
		return permisoDesacumularTodo;
	}

	public void setPermisoDesacumularTodo(boolean permisoDesacumularTodo) {
		this.permisoDesacumularTodo = permisoDesacumularTodo;
	}

	public boolean isPermisoAcumular() {
		return permisoAcumular;
	}

	public void setPermisoAcumular(boolean permisoAcumular) {
		this.permisoAcumular = permisoAcumular;
	}

	public boolean isPermisoRetirar() {
		return permisoRetirar;
	}

	public void setPermisoRetirar(boolean permisoRetirar) {
		this.permisoRetirar = permisoRetirar;
	}

	public boolean isPermisoGenerar() {
		return permisoGenerar;
	}

	public void setPermisoGenerar(boolean permisoGenerar) {
		this.permisoGenerar = permisoGenerar;
	}
	
	

}
