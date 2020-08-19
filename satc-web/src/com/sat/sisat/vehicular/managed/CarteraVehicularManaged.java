package com.sat.sisat.vehicular.managed;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.persistence.entity.RvCategoriaVehiculo;
import com.sat.sisat.persistence.entity.RvClaseVehiculo;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculo;
import com.sat.sisat.persistence.entity.RvOmisosVehicular;
import com.sat.sisat.persistence.entity.RvTipoCarroceria;
import com.sat.sisat.persistence.entity.RvTipoMotor;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.dto.CarteraVehiculosDTO;
import com.sat.sisat.vehicular.dto.DistritoDTO;
import com.sat.sisat.vehicular.dto.MarcaModeloTemporalDTO;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@ViewScoped
public class CarteraVehicularManaged extends BaseManaged{
	
	@EJB
	VehicularBoRemote vehiculoBo;
	
	@EJB
	ControlyCobranzaBoRemote controlBo;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	private List<CarteraVehiculosDTO> lstCarteraVehiculo = new ArrayList<CarteraVehiculosDTO>();
	private CarteraVehiculosDTO omisoItem=new CarteraVehiculosDTO();	
	
	private List<DistritoDTO> lstDistritos = new ArrayList<DistritoDTO>();
	private List<GnSector> lstSector= new ArrayList<GnSector>();
	
	//Combo Box Clase
	private List<RvClaseVehiculo> listaClaseVehiculos = new ArrayList<RvClaseVehiculo>();
	
	//Combo Box Categoria de vehiculo
	private List<RvCategoriaVehiculo> listaCategoriaVehiculos = new ArrayList<RvCategoriaVehiculo>();
	
	//Combo Box Marca de vehiculo
	private List<RvMarca> listaMarcaVehiculos = new ArrayList<RvMarca>();		
	
	//Combo Box Motor de vehiculo
	private List<RvTipoMotor> listaMotorVehiculos = new ArrayList<RvTipoMotor>();
	
	//Combo Box Carroceria de vehiculo
	private List<RvTipoCarroceria> listaCarroceriaVehiculos = new ArrayList<RvTipoCarroceria>();	
	
	//Combo Box Modelo de vehiculo
	private List<RvModeloVehiculo> listaModeloVehiculos = new ArrayList<RvModeloVehiculo>();
	
	private List<MarcaModeloTemporalDTO> lstMarcaTemp = new ArrayList<MarcaModeloTemporalDTO>();
	
	private List<MarcaModeloTemporalDTO> lstModeloTemp = new ArrayList<MarcaModeloTemporalDTO>();
	
	private String marcaTemp ;
	private String modeloTemp ;
	
	
	private boolean editMultiple;
	
	
	private int distritoId;
	private int sectorId;
	private int loteId;
	private int personaId;
	private String placa;
	private String nroRequerimiento;
	
	private boolean selectedAll;
	
	@PostConstruct
	public void init() {
		try {
			editMultiple = false;
			getCombos();
			//lstCarteraVehiculo = vehiculoBo.getCarteraVehiculos(57637, 27, 0, null,0,null);
			
			//System.out.println(loteId);
			//System.out.println(getSessionManaged().getLoteIdVehicular());
			loteId = getSessionManaged().getLoteIdVehicular();
			lstCarteraVehiculo = vehiculoBo.getCarteraVehiculos(loteId, sectorId, personaId, nroRequerimiento,distritoId,placa);
			
			lstDistritos = vehiculoBo.getDistritos();
			lstSector = controlBo.getAllSector();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void activarEdicionMasiva() {
		editMultiple = true;
		lstCarteraVehiculo = new ArrayList<CarteraVehiculosDTO>();
		try {
			lstMarcaTemp = ficalizacionBo.getMarcaModeloTemporal(loteId, 1);
			lstModeloTemp = ficalizacionBo.getMarcaModeloTemporal(loteId, 2);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void inactivarEdicionMasiva() {
		try {
			lstCarteraVehiculo = vehiculoBo.getCarteraVehiculos(loteId, sectorId, personaId, nroRequerimiento,distritoId,placa);
			editMultiple = false;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void getCombos() {
		try {
			/* COMBOBOX:: CLASE DE VEHICULOS */
			listaClaseVehiculos = ficalizacionBo.getAllClaseVehiculos();			
			/* FIN COMBOBOX:: CLASE DE VEHICULOS */
			
			/* COMBOBOX:: CATEGORIA DE VEHICULOS */
			listaCategoriaVehiculos = ficalizacionBo.getAllCategoriaVehiculos();			
												
			/* FIN COMBOBOX:: CATEGORIA DE VEHICULOS */	
			
			/* COMBOBOX:: MOTOR DE VEHICULOS */
			listaMotorVehiculos = ficalizacionBo.getAllMotorVehiculos();			
						
			/* FIN COMBOBOX:: MARCA DE VEHICULOS */	

			/* COMBOBOX:: CARROCERIA DE VEHICULOS */
			listaCarroceriaVehiculos = ficalizacionBo.getAllCarroceriaVehiculos();		
					
			/* FIN COMBOBOX:: CARROCERIA DE VEHICULOS */
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cargarMarca(ValueChangeEvent event) {
		try {
			int id = (int) event.getNewValue();
			listaMarcaVehiculos = ficalizacionBo.findRvMarca(id);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cargarModelos(ValueChangeEvent event) {
		try {
			int id = (int) event.getNewValue();
			listaModeloVehiculos =  ficalizacionBo.getAllRvModeloVehiculo(id, omisoItem.getCategoriaVehiculoId());	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void editarVehiculo(){
		
		try {
			
			vehiculoBo.actualizarVehiculoOmiso(omisoItem.getMarcaVehiculoId(),omisoItem.getCategoriaVehiculoId(),omisoItem.getModeloVehiculoId()
						,omisoItem.getClaseVehiculoId(),omisoItem.getCarroceriaId(),omisoItem.getTipoMotorId(), omisoItem.getNumeroCilindros(), 
						omisoItem.getVehiculoId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn(), DateUtil.getCurrentDate());
		
			
		
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void editarVehiculoMasivo() {
		
		Iterator<CarteraVehiculosDTO> menuIterar = lstCarteraVehiculo.iterator();
		
		while (menuIterar.hasNext()) {
			CarteraVehiculosDTO obj = menuIterar.next();

			try {
				vehiculoBo.actualizarVehiculoOmiso(omisoItem.getMarcaVehiculoId(),omisoItem.getCategoriaVehiculoId(),omisoItem.getModeloVehiculoId()
						,omisoItem.getClaseVehiculoId(),omisoItem.getCarroceriaId(),omisoItem.getTipoMotorId(), obj.getNumeroCilindros(), 
						obj.getVehiculoId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn(), DateUtil.getCurrentDate());
				
			} catch (SisatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		buscarEdicionMultiple();
	}
	
	public void cargaItem(CarteraVehiculosDTO item) {
		try {
			if(item.getCarroceriaId() > 0) {
				
					listaMarcaVehiculos = ficalizacionBo.findRvMarca(item.getCarroceriaId());
				
			}
			
			if(item.getCarroceriaId() > 0 && item.getMarcaVehiculoId() > 0) {
				listaModeloVehiculos =  ficalizacionBo.getAllRvModeloVehiculo(item.getMarcaVehiculoId(), item.getCategoriaVehiculoId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actualizarEdicionMultiple(){
		
	}
	public void buscar() {		
		try {
			lstCarteraVehiculo = vehiculoBo.getCarteraVehiculos(loteId, sectorId, personaId, nroRequerimiento,distritoId,placa);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buscarEdicionMultiple() {		
		try {
			lstCarteraVehiculo = vehiculoBo.getCarteraVehiculosEditarMasivo(loteId, marcaTemp, modeloTemp);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void nuevo() {
		
	}
	
	public void limpiar() {
		
	}
	
	
	public void generacionReqCarteraVehicular() {
		try {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					
					parameters.put("ruta_imagen", path_imagen);
					parameters.put("p_lote_id", loteId);
					parameters.put("p_sector_id", sectorId);
					parameters.put("p_nro_requerimiento", nroRequerimiento);
					parameters.put("p_distrito_id", distritoId);
					parameters.put("p_placa", placa);
					parameters.put("persona_id", personaId);
					

					JasperPrint jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "req_cartera_vehicular.jasper"),
									parameters, connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=req_cartera_vehicular.pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0,
							output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
				} finally {
					try {
						if (connection != null) {
							connection.close();
							connection = null;
						}
					} catch (Exception e) {
					}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void changeSelectAll(ValueChangeEvent ev){
		String nv = ev.getNewValue().toString();
		
		if(nv.equals("true")){
			for(CarteraVehiculosDTO de : lstCarteraVehiculo){
				de.setSelected(true);
				System.out.println(de.getPlaca());
			}
		}else{
			for(CarteraVehiculosDTO de : lstCarteraVehiculo){
				de.setSelected(false);
			}
		}
	}

	public List<CarteraVehiculosDTO> getLstCarteraVehiculo() {
		return lstCarteraVehiculo;
	}

	public void setLstCarteraVehiculo(List<CarteraVehiculosDTO> lstCarteraVehiculo) {
		this.lstCarteraVehiculo = lstCarteraVehiculo;
	}

	public List<DistritoDTO> getLstDistritos() {
		return lstDistritos;
	}

	public void setLstDistritos(List<DistritoDTO> lstDistritos) {
		this.lstDistritos = lstDistritos;
	}

	public int getDistritoId() {
		return distritoId;
	}

	public void setDistritoId(int distritoId) {
		this.distritoId = distritoId;
	}

	public List<GnSector> getLstSector() {
		return lstSector;
	}

	public void setLstSector(List<GnSector> lstSector) {
		this.lstSector = lstSector;
	}

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public int getLoteId() {
		return loteId;
	}

	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getNroRequerimiento() {
		return nroRequerimiento;
	}

	public void setNroRequerimiento(String nroRequerimiento) {
		this.nroRequerimiento = nroRequerimiento;
	}

	public CarteraVehiculosDTO getOmisoItem() {
		return omisoItem;
	}

	public void setOmisoItem(CarteraVehiculosDTO omisoItem) {
		this.omisoItem = omisoItem;
	}
	public List<RvClaseVehiculo> getListaClaseVehiculos() {
		return listaClaseVehiculos;
	}


	public void setListaClaseVehiculos(List<RvClaseVehiculo> listaClaseVehiculos) {
		this.listaClaseVehiculos = listaClaseVehiculos;
	}


	public List<RvCategoriaVehiculo> getListaCategoriaVehiculos() {
		return listaCategoriaVehiculos;
	}


	public void setListaCategoriaVehiculos(List<RvCategoriaVehiculo> listaCategoriaVehiculos) {
		this.listaCategoriaVehiculos = listaCategoriaVehiculos;
	}


	public List<RvMarca> getListaMarcaVehiculos() {
		return listaMarcaVehiculos;
	}


	public void setListaMarcaVehiculos(List<RvMarca> listaMarcaVehiculos) {
		this.listaMarcaVehiculos = listaMarcaVehiculos;
	}


	public List<RvTipoMotor> getListaMotorVehiculos() {
		return listaMotorVehiculos;
	}


	public void setListaMotorVehiculos(List<RvTipoMotor> listaMotorVehiculos) {
		this.listaMotorVehiculos = listaMotorVehiculos;
	}


	public List<RvTipoCarroceria> getListaCarroceriaVehiculos() {
		return listaCarroceriaVehiculos;
	}


	public void setListaCarroceriaVehiculos(List<RvTipoCarroceria> listaCarroceriaVehiculos) {
		this.listaCarroceriaVehiculos = listaCarroceriaVehiculos;
	}


	public List<RvModeloVehiculo> getListaModeloVehiculos() {
		return listaModeloVehiculos;
	}


	public void setListaModeloVehiculos(List<RvModeloVehiculo> listaModeloVehiculos) {
		this.listaModeloVehiculos = listaModeloVehiculos;
	}


	public boolean isEditMultiple() {
		return editMultiple;
	}


	public void setEditMultiple(boolean editMultiple) {
		this.editMultiple = editMultiple;
	}

	public List<MarcaModeloTemporalDTO> getLstMarcaTemp() {
		return lstMarcaTemp;
	}

	public void setLstMarcaTemp(List<MarcaModeloTemporalDTO> lstMarcaTemp) {
		this.lstMarcaTemp = lstMarcaTemp;
	}

	public List<MarcaModeloTemporalDTO> getLstModeloTemp() {
		return lstModeloTemp;
	}

	public void setLstModeloTemp(List<MarcaModeloTemporalDTO> lstModeloTemp) {
		this.lstModeloTemp = lstModeloTemp;
	}

	public String getMarcaTemp() {
		return marcaTemp;
	}

	public void setMarcaTemp(String marcaTemp) {
		this.marcaTemp = marcaTemp;
	}

	public String getModeloTemp() {
		return modeloTemp;
	}

	public void setModeloTemp(String modeloTemp) {
		this.modeloTemp = modeloTemp;
	}

	public boolean isSelectedAll() {
		return selectedAll;
	}

	public void setSelectedAll(boolean selectedAll) {
		this.selectedAll = selectedAll;
	}
	
	

}
