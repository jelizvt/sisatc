package com.sat.sisat.vehicular.managed;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.cns.VehicularCns;
import com.sat.sisat.vehicular.dto.BuscarVehicularDTO;

@ManagedBean
@ViewScoped
public class NuevadjBuscaVehiculoManaged extends BaseManaged {

	private static final long serialVersionUID = -3006012768528424253L;

	@EJB
	VehicularBoRemote vehicularBo;

	private String placaBuscar;
	private String motorBuscar;
	private String mensaje;
	private boolean renderedMsgMotor;

	private boolean renderedBotonContinuar = false;

	private List<BuscarVehicularDTO> lstExistentes;
	private Integer vehicExistenteId;

	private String accion = null;
	
	

	public NuevadjBuscaVehiculoManaged() {
		getSessionManaged().setLinkRegresar("/sisat/vehicular/buscarvehicular.xhtml");
	}

	public String buscarVehiculo() {
		lstExistentes = new ArrayList<BuscarVehicularDTO>();
		mensaje = null;
		renderedMsgMotor = false;
		renderedBotonContinuar = false;
		
		try {
			
			vehicExistenteId = vehicularBo.existeVehiculoPlaca(placaBuscar);

			if (vehicExistenteId != null) { // vehículo existe
				
				if(vehicularBo.isVehiculoAssociToDj(vehicExistenteId)){ // Asociado a djv
					lstExistentes = vehicularBo.findDjVehicular(getSessionManaged().getContribuyente().getPersonaId(), vehicExistenteId);
					if(lstExistentes.isEmpty()){ // Existe para otro contribuyente
						
						lstExistentes = vehicularBo.findDjVehicularVehicId(vehicExistenteId);
						if(lstExistentes.size()==0){
							addErrorMessage("Ha ocurrido un error en la busqueda");
							return null;
						}
						
						BuscarVehicularDTO bdj = lstExistentes.get(0);
						if(bdj.getEstado().equals("1") && bdj.getMotivoDeclaId() == Constante.MOTIVO_DECLARACION_DESCARGO.intValue()){ // Vehiculo descargado
							mensaje = getMsg("rv.vehiculoexistedescargado");
						}else{ // No esta descargado.
							if(bdj.getEstado().equals(Constante.ESTADO_PENDIENTE)){
								mensaje = getMsg("rv.vehiculoexistependienteaprobacion");
							}else{
								mensaje = getMsg("rv.vehiculodeclaradotrocontrib");
							}
						}
						renderedBotonContinuar = true;
						accion = VehicularCns.INSCRIPCION;
					}else{ // Existe para éste contribuyente
						mensaje = getMsg("rv.vehiculodeclaradomismocontrib");
					}
				}else{ // No Asociado a djv
					mensaje = getMsg("rv.vehiculoexiste");
					renderedBotonContinuar = true;
					accion = VehicularCns.PRIMERA_INSCRIPCION_VEHIC;
				}
			} else { // Vehículo no existe
				
				if (!motorBuscar.isEmpty()) { // Solo para mostrar alerta de que motor esta registrado en otro vehículo.
					Integer vExist = vehicularBo.existeVehiculoMotor(motorBuscar);
					if(vExist!=null){
						renderedMsgMotor = true;
					}
				}
				
				mensaje = getMsg("rv.vehiculonodeclarado");
				renderedBotonContinuar = true;
				accion = VehicularCns.PRIMERA_INSCRIPCION_CERO;
			}
			
			/*
			 * Se agregaron estas líneas de código,para registrar operaciones en la tabla gn_auditoria_operacion,para el Reporte del Dpto. de Servicios.-29.01.2015
			 */
//			auditoria = new GnAuditoriaOperacion();
//
//			auditoria.setTablaNombre("rv_djvehicular");
//			auditoria.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
//			auditoria.setContribuyente(getSessionManaged().getContribuyente().getApellidosNombres());
//			auditoria.setCodigoOperacion("NuevaDj");
//			auditoria.setPlaca(placaBuscar);
//			auditoria.setTipoOperacion(Constante.OPERACION_BUSQUEDA);
//			auditoria.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
//			auditoria.setTerminalRegistro(getSessionManaged().getTerminalLogIn());
//			try {
//				vehicularBo.guardarOperacionAuditoria(auditoria);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} catch (Exception ex) {
			// Controller exception
			System.out.println("Error buscando vehículo: " + ex);
		}
		return null;
	}

	public String continuar() {
		try {
			if (accion.equals(VehicularCns.PRIMERA_INSCRIPCION_CERO)) {
				getSessionMap().put("accion",VehicularCns.PRIMERA_INSCRIPCION_CERO);
				getSessionMap().remove("nuevadjregistro.placa");
				getSessionMap().remove("nuevadjregistro.motor");
				if (!placaBuscar.isEmpty()) {
					getSessionMap().put("nuevadjregistro.placa", placaBuscar);
				}
				if (!motorBuscar.isEmpty()) {
					getSessionMap().put("nuevadjregistro.motor", motorBuscar);
				}
			} else if (accion.equals(VehicularCns.PRIMERA_INSCRIPCION_VEHIC)) {
				getSessionMap().put("accion",VehicularCns.PRIMERA_INSCRIPCION_VEHIC);
				getSessionMap().put("nuevadjregistro.vehicId", vehicExistenteId);
			} else if (accion.equals(VehicularCns.INSCRIPCION)) {
				getSessionMap().put("accion", VehicularCns.INSCRIPCION);
				getSessionMap().put("nuevadjregistro.djvId", lstExistentes.get(0).getDjVehicularId());
			}
		} catch (Exception ex) {
			// Controller exception
			System.out.println("ERROR: " + ex);
		}
		return sendRedirectPrincipal();
	}

	public String getPlacaBuscar() {
		return placaBuscar;
	}

	public void setPlacaBuscar(String placaBuscar) {
		this.placaBuscar = placaBuscar;
	}

	public String getMotorBuscar() {
		return motorBuscar;
	}

	public void setMotorBuscar(String motorBuscar) {
		this.motorBuscar = motorBuscar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public boolean isRenderedBotonContinuar() {
		return renderedBotonContinuar;
	}

	public List<BuscarVehicularDTO> getLstExistentes() {
		return lstExistentes;
	}

	public boolean isRenderedMsgMotor() {
		return renderedMsgMotor;
	}
}
