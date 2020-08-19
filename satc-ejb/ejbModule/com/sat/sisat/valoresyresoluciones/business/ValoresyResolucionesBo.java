package com.sat.sisat.valoresyresoluciones.business;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.GnCondicionEspecialPK;
import com.sat.sisat.persistence.entity.MpCondicionEspecialRequisito;
import com.sat.sisat.persistence.entity.MpInspeccionCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoDocumentoCondicionEspecial;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.persistence.entity.ReEstadoResolucion;
import com.sat.sisat.persistence.entity.TdRequisitoExpediente;
import com.sat.sisat.predial.business.BaseBusiness;
import com.sat.sisat.predial.dto.MpRequerimientoCondicionEspecialDTO;
import com.sat.sisat.predial.dto.SustentoCondicionEspecialDTO;
import com.sat.sisat.valoresyresoluciones.dao.ValoresyResolucionesBusinessDao;
import com.sat.sisat.valoresyresoluciones.dto.BuscarActoCoactivoDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarActoOrdinarioDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarDescargoDTO;
import com.sat.sisat.valoresyresoluciones.dto.DatosActoDTO;
import com.sat.sisat.valoresyresoluciones.dto.PagosActoDTO;

@Stateless
public class ValoresyResolucionesBo extends BaseBusiness implements ValoresyResolucionesBoRemote{


	private static final long serialVersionUID = 1L;

	@Override
	public List<BuscarActoOrdinarioDTO> getAllActosOrdinarios(Integer personaId,String nroActo) throws Exception {
		return getService().getAllActosOrdinarios(personaId,nroActo);
	}

	private ValoresyResolucionesBusinessDao service;
	
	public ValoresyResolucionesBusinessDao getService(){
		return this.service;
	}
	
	@PostConstruct
    public void initialize(){
		this.service=new ValoresyResolucionesBusinessDao();
		setDataManager(this.service);
	}

	@Override
	public List<BuscarActoCoactivoDTO> getAllActosCoactivos(Integer personaId,String nroRec)
			throws Exception {
		return getService().getAllActosCoactivos(personaId, nroRec);
	}

	@Override
	public List<DatosActoDTO> getAllDatosActo(int actoId)
			throws Exception {
		return getService().getAllDatosActo(actoId);
	}

	@Override
	public List<PagosActoDTO> getPagosActo(int deudaId) throws Exception {
		return getService().getPagosActo(deudaId);
	}
	
	
	public List<MpCondicionEspecialRequisito> getAllRequisitoByCondicionEspecial(int tipo,int codigo)throws Exception {
		return getService().getAllRequisitoByCondicionEspecial(tipo,codigo);
	}
	
	public Integer registrarRequerimiento(Integer codigo,
			Integer tipoCondicion, String contribuyente, String direccion,
			Integer tipoPresentacion, Integer usuarioId, String terminal)
			throws Exception {
		return getService().registrarRequerimiento(codigo, tipoCondicion,
				contribuyente, direccion,tipoPresentacion, getUser().getUsuarioId(),
				getUser().getTerminal());
	}

	public Integer registrarSustento(Integer codigo,
			Integer condicionRequisito, String glosa,Integer flag,Integer id,Integer usuarioId,
			String terminal) throws Exception {
		return getService().registrarSustento(codigo, condicionRequisito,
				glosa,flag,id, getUser().getUsuarioId(), getUser().getTerminal());
	}
	
	public List<SustentoCondicionEspecialDTO> getAllRequisitoByCondicionByPersona(int codigo)throws Exception {
		return getService().getAllRequisitoByCondicionByPersona(codigo);
	}
	
	public List<MpTipoCondicionEspecial> getAllMpTipoCondicionEspecial(Integer tipoPersonaId, Integer subtipoPersonaId)
			throws Exception {
		return getService().getAllMpTipoCondicionEspecial(tipoPersonaId, subtipoPersonaId);
	}
	
	public List<ReEstadoResolucion> getAllEstadoCondicionEspecial(int id)
			throws Exception {
		return getService().getAllEstadoCondicionEspecial(id);
	}
	
	public List<MpRequerimientoCondicionEspecialDTO> getAllBandejaCondicionEspecial(int codigo)throws Exception {
		return getService().getAllBandejaCondicionEspecial(codigo);
	}
	
	public List<NoMotivoNotificacion> getAlNoMotivoNotificacion(Integer flagUbicacion)	throws Exception{
		  return getService().getAlNoMotivoNotificacion(flagUbicacion);
    }
	
	public List<NoNotificador> getAllNotificador()	throws Exception{
		  return getService().getAllNotificador();
	}
	
	
	public Integer registrarNotificacion(Integer requerimiento, Integer motivo,
			Integer notificador, Date fecha_notificacion, String observacion,Integer tipo,Integer usuarioId,String terminal)
			throws Exception {
		return getService().registrarNotificacion(requerimiento, motivo,
				notificador, fecha_notificacion, observacion,tipo,
				getUser().getUsuarioId(), getUser().getTerminal());
	}
	
	public List<MpTipoDocumentoCondicionEspecial> getAllMpTipoDocumentoCondicionEspecial() throws Exception {
		return getService().getAllMpTipoDocumentoCondicionEspecial();
	}
	
	public String getAllCorrelativoCondicionEspecial(Integer tipoDocId) throws Exception{
 		return getService().getAllCorrelativoCondicionEspecial(tipoDocId);
	}
	
	public Integer registrarResolucion(GnCondicionEspecial condicionEspecial,String tabla,Integer codigo,Integer usuarioId,String terminal)
			throws Exception {
		return getService().registrarResolucion(condicionEspecial,tabla,codigo,
				getUser().getUsuarioId(), getUser().getTerminal());
	}
	
	public List<MpCondicionEspecialRequisito> obtenerRequisitosExpediente(Integer expedienteId,Integer codigo) throws Exception {
		return getService().obtenerRequisitosExpediente(expedienteId,codigo);
	}

	public List<MpTipoCondicionEspecial> obtenerCondicionExpediente(Integer requerimientoId)throws Exception {
		return getService().obtenerCondicionExpediente(requerimientoId);
	}
	
	public Integer actualizarSustento(Integer codigo,
			Integer condicionRequisito, String glosa,Integer flag,Integer id,Integer usuarioId,
			String terminal,Integer tipo) throws Exception {
		return getService().actualizarSustento(codigo, condicionRequisito,
				glosa,flag,id, getUser().getUsuarioId(), getUser().getTerminal(),tipo);
	}
	
	public Integer cierreRequerimiento(Integer codigo,Integer id, Integer usuarioId,String terminal) throws Exception {
		return getService().cierreRequerimiento(codigo,id, getUser().getUsuarioId(), getUser().getTerminal());
	}
	public Integer registrarInspecion(MpInspeccionCondicionEspecial mpInspeccionCondicionEspecial, Integer tipo) throws Exception{
		return getService().registrarInspecion(mpInspeccionCondicionEspecial,tipo);
	}

	public List<BuscarDescargoDTO> obtenerCondicionDeuda(Integer personaId)throws Exception {
		return getService().obtenerCondicionDeuda(personaId);
	}
}
