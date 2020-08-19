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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.FindParameterDto;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.cobranzacoactiva.dto.ListadoEstadoTransferencia;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;

@ManagedBean
@ViewScoped
public class TransferenciaValoresManaged extends BaseManaged {
	private static final long serialVersionUID = 1673161260001450282L;
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private Integer nroLoteOrigen;
	private Integer periodoDeuda;
	
	private Integer estadoTransferencia;
	
	private List<InformeTransferido> records = new ArrayList<InformeTransferido>();
	private InformeTransferido selInformeTransferido = new InformeTransferido();
	
	private List<InformeTransferidoDetalle> recordsDetalle = new ArrayList<InformeTransferidoDetalle>();
	
	private List<ListadoEstadoTransferencia> listaEstado = new ArrayList<ListadoEstadoTransferencia>();
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	 	private boolean permisoBuscar;	 	
	 	private boolean permisoVerDetalle;
	 	private boolean permisoRecepciona;
	 // FIN PERMISOS PARA EL MODULO

	@PostConstruct
	public void init() throws Exception {
		permisosMenu();
		try {
			listaEstado=cobranzaCoactivaBo.listarEstadoTransferencia();
			if(getParameterSession()){
				buscar();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.TRANSFERENCIAS;
		 	
			int permisoBuscarId = Constante.BUSCAR;
			int permisoVerDetalleId = Constante.VER_DETALLE;
			int permisoRecepcionaId = Constante.RECEPCIONA;
			
			permisoBuscar = false;
			permisoVerDetalle = false;
			permisoRecepciona = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisoVerDetalleId) {
					permisoVerDetalle = true;
				}
				if(lsm.getItemId() == permisoRecepcionaId) {
					permisoRecepciona = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void buscar() {
		try{	
			setParameterSession();
			records=cobranzaCoactivaBo.buscarInformeTransferido(nroLoteOrigen, periodoDeuda, estadoTransferencia);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setParameterSession(){
		if(nroLoteOrigen!=null&&nroLoteOrigen>0){
			FindParameterDto findParameter=new FindParameterDto("nroLoteOrigen",nroLoteOrigen); 
			getSessionMap().put("findParameter", findParameter);
		}else if(periodoDeuda!=null&&periodoDeuda>0){
			FindParameterDto findParameter=new FindParameterDto("periodoDeuda",periodoDeuda); 
			getSessionMap().put("findParameter", findParameter);
		}else if(estadoTransferencia!=null&&estadoTransferencia>0){
			FindParameterDto findParameter=new FindParameterDto("estadoTransferencia",estadoTransferencia); 
			getSessionMap().put("findParameter", findParameter);
		}
	}
	public boolean getParameterSession(){
		FindParameterDto findParameter=(FindParameterDto)getSessionMap().get("findParameter");
		if(findParameter!=null){
			if(findParameter.getParameterName().equals("nroLoteOrigen")){
				nroLoteOrigen=Integer.valueOf(findParameter.getParameterValue());
				return true;	
			}else if(findParameter.getParameterName().equals("periodoDeuda")){
				periodoDeuda=Integer.valueOf(findParameter.getParameterValue());
				return true;
			}else if(findParameter.getParameterName().equals("estadoTransferencia")){
				estadoTransferencia=Integer.valueOf(findParameter.getParameterValue());
				return true;
			}
		}
		return false;
	}
	
	public void verDetalle()throws Exception{
		try{
			if(selInformeTransferido!=null){
				recordsDetalle=cobranzaCoactivaBo.buscarDetalleInformeTransferido(selInformeTransferido.getLoteTransferenciaId());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String gestionValores()throws Exception{
		try{
			FacesUtil.closeSession("recepcionValoresManaged");
			getSessionMap().put("LoteTransferencia", selInformeTransferido);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendRedirectPrincipal();
	}
	
	public void exportXls() {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Reporte");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		
		row1.createCell(0).setCellValue("item");
		row1.createCell(1).setCellValue("Tipo de valor");
		row1.createCell(2).setCellValue("Concepto");
		row1.createCell(3).setCellValue("Periodo");
		row1.createCell(4).setCellValue("Fecha de envío");
		row1.createCell(5).setCellValue("Usuario");
		row1.createCell(6).setCellValue("Nro Lote origen");
		row1.createCell(7).setCellValue("Cant. valor");
		row1.createCell(8).setCellValue("Cant. recibida");
		row1.createCell(9).setCellValue("Cant. devuelta");
		row1.createCell(10).setCellValue("Total exigible");
		row1.createCell(11).setCellValue("Estado");

		for (InformeTransferido data : records) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getPeriodoDeuda());
			row.createCell(columnIndex++).setCellValue(data.getFechaEmision());
			row.createCell(columnIndex++).setCellValue(data.getUsuarioEmision());
			row.createCell(columnIndex++).setCellValue(data.getNroLoteOrigen());
			row.createCell(columnIndex++).setCellValue(data.getCantidadValores());
			row.createCell(columnIndex++).setCellValue(data.getCantidadRecibido());
			row.createCell(columnIndex++).setCellValue(data.getCantidadDevuelto());
			row.createCell(columnIndex++).setCellValue(data.getTotalExigible());
			row.createCell(columnIndex++).setCellValue(data.getEstadoTransferencia());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=consulta_lote_transferencia.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	public void exportDetalleXls() {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Reporte");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		
		row1.createCell(0).setCellValue("item");
		row1.createCell(1).setCellValue("Tipo de valor");
		row1.createCell(2).setCellValue("Concepto");
		row1.createCell(3).setCellValue("Periodo");
		row1.createCell(4).setCellValue("Nro Valor");
		row1.createCell(5).setCellValue("Código");
		row1.createCell(6).setCellValue("Contribuyente");
		row1.createCell(7).setCellValue("Dirección fiscal");
		row1.createCell(8).setCellValue("Total Deuda");
		row1.createCell(9).setCellValue("Estado Deuda");
		row1.createCell(10).setCellValue("Exigibilidad");
		row1.createCell(11).setCellValue("Estado");

		for (InformeTransferidoDetalle data : recordsDetalle) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getPeriodo());
			row.createCell(columnIndex++).setCellValue(data.getNroValor());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresPersona());
			row.createCell(columnIndex++).setCellValue(data.getDireccionFiscal());
			row.createCell(columnIndex++).setCellValue(data.getMontoDeuda());
			row.createCell(columnIndex++).setCellValue(data.getEstadoDeuda());
			row.createCell(columnIndex++).setCellValue(data.getExigibilidad());
			row.createCell(columnIndex++).setCellValue(data.getEstadoDeuda());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=consulta_detalle_lote_transferencia.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	public Integer getNroLoteOrigen() {
		return nroLoteOrigen;
	}

	public void setNroLoteOrigen(Integer nroLoteOrigen) {
		this.nroLoteOrigen = nroLoteOrigen;
	}

	public Integer getPeriodoDeuda() {
		return periodoDeuda;
	}

	public void setPeriodoDeuda(Integer periodoDeuda) {
		this.periodoDeuda = periodoDeuda;
	}

	public Integer getEstadoTransferencia() {
		return estadoTransferencia;
	}

	public void setEstadoTransferencia(Integer estadoTransferencia) {
		this.estadoTransferencia = estadoTransferencia;
	}

	public List<InformeTransferido> getRecords() {
		return records;
	}
	public void setRecords(List<InformeTransferido> records) {
		this.records = records;
	}
	public InformeTransferido getSelInformeTransferido() {
		return selInformeTransferido;
	}

	public void setSelInformeTransferido(InformeTransferido selInformeTransferido) {
		this.selInformeTransferido = selInformeTransferido;
	}
	public List<InformeTransferidoDetalle> getRecordsDetalle() {
		return recordsDetalle;
	}

	public void setRecordsDetalle(List<InformeTransferidoDetalle> recordsDetalle) {
		this.recordsDetalle = recordsDetalle;
	}
	
	public List<ListadoEstadoTransferencia> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<ListadoEstadoTransferencia> listaEstado) {
		this.listaEstado = listaEstado;
	}


	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}


	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}


	public boolean isPermisoBuscar() {
		return permisoBuscar;
	}


	public void setPermisoBuscar(boolean permisoBuscar) {
		this.permisoBuscar = permisoBuscar;
	}


	public boolean isPermisoVerDetalle() {
		return permisoVerDetalle;
	}


	public void setPermisoVerDetalle(boolean permisoVerDetalle) {
		this.permisoVerDetalle = permisoVerDetalle;
	}


	public boolean isPermisoRecepciona() {
		return permisoRecepciona;
	}


	public void setPermisoRecepciona(boolean permisoRecepciona) {
		this.permisoRecepciona = permisoRecepciona;
	}
	
}
