package com.sat.sisat.valoresyresoluciones.business;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.MpCondicionEspecialRequisito;
import com.sat.sisat.persistence.entity.MpInspeccionCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoDocumentoCondicionEspecial;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.persistence.entity.ReEstadoResolucion;
import com.sat.sisat.persistence.entity.TdRequisitoExpediente;
import com.sat.sisat.predial.dto.MpRequerimientoCondicionEspecialDTO;
import com.sat.sisat.predial.dto.SustentoCondicionEspecialDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarActoCoactivoDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarActoOrdinarioDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarDescargoDTO;
import com.sat.sisat.valoresyresoluciones.dto.DatosActoDTO;
import com.sat.sisat.valoresyresoluciones.dto.PagosActoDTO;

@Local
public interface ValoresyResolucionesBoLocal {

	public List<BuscarActoOrdinarioDTO> getAllActosOrdinarios(Integer personaId,String nroActo)throws Exception;
	
	public List<BuscarActoCoactivoDTO> getAllActosCoactivos(Integer personaId,String nroRec)throws Exception;

	public List<DatosActoDTO> getAllDatosActo(int personaId, int actoId )throws Exception;
	
	public List<PagosActoDTO> getPagosActo(int deudaId)throws Exception;
	
	public abstract List<MpCondicionEspecialRequisito> getAllRequisitoByCondicionEspecial(int tipo,int codigo) throws Exception;
	
	public Integer registrarRequerimiento(Integer codigo,Integer tipoCondicion, String contribuyente, String direccion,Integer tipoPresentacion,Integer usuarioId, String terminal) throws Exception;
	
	public Integer registrarSustento(Integer codigo, Integer condicionRequisito, String glosa,Integer flag,Integer id, Integer usuarioId,String terminal) throws Exception;
	
	public abstract List<SustentoCondicionEspecialDTO> getAllRequisitoByCondicionByPersona(int codigo) throws Exception;
	
	public abstract List<MpTipoCondicionEspecial> getAllMpTipoCondicionEspecial(Integer tipoPersonaId,Integer subTipoPersonaId)throws Exception;
	
	public abstract List<ReEstadoResolucion> getAllEstadoCondicionEspecial(int id) throws Exception;
	
	public abstract List<MpRequerimientoCondicionEspecialDTO> getAllBandejaCondicionEspecial(int codigo) throws Exception;
	
	public List<NoMotivoNotificacion> getAlNoMotivoNotificacion(Integer flagUbicacion) throws Exception;
	
	public List<NoNotificador> getAllNotificador() throws Exception;
	
	public Integer registrarNotificacion(Integer requerimiento,Integer motivo, Integer notificador,Date fecha_notificacion,String observacion,Integer tipo,Integer usuarioId,String terminal) throws Exception;
	
	public abstract  List<MpTipoDocumentoCondicionEspecial> getAllMpTipoDocumentoCondicionEspecial()throws Exception;
	
	public String getAllCorrelativoCondicionEspecial(Integer tipoDocId) throws Exception;
	
	public Integer registrarResolucion(GnCondicionEspecial condicionEspecial,String tabla,Integer codigo,Integer usuarioId,String terminal) throws Exception;
	
	public List<MpCondicionEspecialRequisito> obtenerRequisitosExpediente(Integer expedienteId) throws Exception;
	
	public abstract List<MpTipoCondicionEspecial> obtenerCondicionExpediente(Integer requerimientoId)throws Exception;
	
	public Integer actualizarRequerimiento(Integer codigo,Integer tipoCondicion, String contribuyente, String direccion,Integer tipoPresentacion,Integer usuarioId, String terminal) throws Exception;
	
	public Integer actualizarSustento(Integer codigo, Integer condicionRequisito, String glosa,Integer flag,Integer id, Integer usuarioId,String terminal) throws Exception;
	
	public Integer cierreRequerimiento(Integer codigo,Integer id, Integer usuarioId,String terminal) throws Exception;
	
	public Integer registrarInspecion(MpInspeccionCondicionEspecial mpInspeccionCondicionEspecial, Integer tipo) throws Exception;
	
	public abstract List<BuscarDescargoDTO> obtenerCondicionDeuda(Integer personaId)throws Exception;

}
