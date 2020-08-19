/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sat.sisat.predial.business;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.DtArancelUbicacion;
import com.sat.sisat.persistence.entity.DtCercaniaParque;
import com.sat.sisat.persistence.entity.DtFactorOfic;
import com.sat.sisat.persistence.entity.DtFrecuenciaLimpieza;
import com.sat.sisat.persistence.entity.DtFrecuenciaRecojo;
import com.sat.sisat.persistence.entity.DtGrupoCercania;
import com.sat.sisat.persistence.entity.DtZonaSeguridad;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUbicacion;
import com.sat.sisat.persistence.entity.GnDenominacionUrbana;
import com.sat.sisat.persistence.entity.GnLugar;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoAgrupamiento;
import com.sat.sisat.persistence.entity.GnTipoDenoUrbana;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnTipoEdificacion;
import com.sat.sisat.persistence.entity.GnTipoIngreso;
import com.sat.sisat.persistence.entity.GnTipoInterior;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnUbicacion;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.persistence.entity.MpCondiEspePredio;
import com.sat.sisat.persistence.entity.MpPredio;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.RjComponenteConstruccion;
import com.sat.sisat.persistence.entity.RjDocuAnexo;
import com.sat.sisat.persistence.entity.RjEstadoConservacion;
import com.sat.sisat.persistence.entity.RjMes;
import com.sat.sisat.persistence.entity.RjTipoDepreciacion;
import com.sat.sisat.persistence.entity.RpAltitud;
import com.sat.sisat.persistence.entity.RpCategoriaObra;
import com.sat.sisat.persistence.entity.RpCategoriaRustico;
import com.sat.sisat.persistence.entity.RpCondicionPropiedad;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpDjusoDetalle;
import com.sat.sisat.persistence.entity.RpFiscaInspeccion;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.persistence.entity.RpMaterialPredominante;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.persistence.entity.RpSustentoPredial;
import com.sat.sisat.persistence.entity.RpTipoAdquisicion;
import com.sat.sisat.persistence.entity.RpTipoNivel;
import com.sat.sisat.persistence.entity.RpTipoObra;
import com.sat.sisat.persistence.entity.RpTipoObraPeriodo;
import com.sat.sisat.persistence.entity.RpTipoTierraRustico;
import com.sat.sisat.persistence.entity.RpTipoUso;
import com.sat.sisat.persistence.entity.RpTramoUso;
import com.sat.sisat.persistence.entity.RpTransferenciaPropiedad;
import com.sat.sisat.persistence.entity.RpUbicacionPredio;
import com.sat.sisat.persistence.entity.TgTabla;
import com.sat.sisat.predial.dao.PredialBusinessDao;
import com.sat.sisat.predial.dto.FindDireccion;
import com.sat.sisat.predial.dto.FindPersona;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.predial.dto.FindRpTipoObraDTO;
import com.sat.sisat.predial.dto.FotoPredioConstruccionesDTO;
import com.sat.sisat.predial.dto.FotoPredioDTO;
import com.sat.sisat.predial.dto.FotoPredioInspeccionDTO;
import com.sat.sisat.predial.dto.FotoPredioInstalacionesDTO;
import com.sat.sisat.predial.dto.ListRpDjPredial;
import com.sat.sisat.predial.dto.RelacionadosDTO;
import com.sat.sisat.predial.dto.UbicacionDTO;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

/**
 *
 * @author cchaucca
 */

@Stateless
public class RegistroPrediosBo extends BaseBusiness implements RegistroPrediosBoRemote{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -261614689996529636L;
	
	private PredialBusinessDao service;
	
	@PostConstruct
    public void initialize(){
    	this.service=new PredialBusinessDao();
    	setDataManager(this.service);
    }
    
    //----------------------------------
    public PredialBusinessDao getService() {
    	return this.service;
	}
    
    public ArrayList<FindRpDjPredial> getRpDjpredial(String apellidosNombres,String razonSocial,Integer tipoDocIdentidad,String numeroDocIdentidad,String codigoPredio,
			Integer tipoViaId,Integer viaId,Integer sectorId,Integer lugarId,String direccion,
			Integer djId,Integer personaId,Boolean esPropietario)throws Exception{
    	return getService().getRpDjpredial(apellidosNombres, razonSocial, tipoDocIdentidad, numeroDocIdentidad, codigoPredio, tipoViaId, viaId, sectorId, lugarId, direccion, djId,personaId,esPropietario);
    }
  	
	public List<GnTipoVia> getAllGnTipoVia() throws Exception{
		return getService().getAllGnTipoVia();
	}

	//rp_ubicacion_predio
	public List<RpUbicacionPredio> getAllRpUbicacionPredio()
			throws Exception{
		return getService().getAllRpUbicacionPredio();
	}

	public List<RpTipoAdquisicion> getAllRpTipoAdquisicion()
			throws Exception{
		return getService().getAllRpTipoAdquisicion();
	}

	//Registro de datos de la construccion
	//Tipo nivel
	public List<RpTipoNivel> getAllRpTipoNivel() throws Exception{
		return getService().getAllRpTipoNivel();
	}

	//Mes enero - diciembre

	//--UBICACION DE PREDIO
	//GnTipoAgrupamiento
	public List<GnTipoAgrupamiento> getAllGnTipoAgrupamiento()
			throws Exception{
		return getService().getAllGnTipoAgrupamiento();
	}

	//GnTipoEdificacion
	public List<GnTipoEdificacion> getAllGnTipoEdificacion()
			throws Exception{
		return getService().getAllGnTipoEdificacion();
	}

	//GnTipoIngreso
	public List<GnTipoIngreso> getAllGnTipoIngreso() throws Exception{
		return getService().getAllGnTipoIngreso();
	}

	//GnTipoInterior
	public List<GnTipoInterior> getAllGnTipoInterior()
			throws Exception{
		return getService().getAllGnTipoInterior();
	}

	//GnSector
	public List<GnSector> getAllGnSector() throws Exception{
		return getService().getAllGnSector();
	}

	//Buscar vias 
	public ArrayList<UbicacionDTO> findGnVia(Integer tipo_via_id,
			Integer sector_id, String descripcion) throws Exception{
		return getService().findGnVia(tipo_via_id, sector_id, descripcion);
	}

	//Buscar vias 
	public int guardarRpDjdireccion(RpDjdireccion direccion)throws Exception{
		Integer Id=getService().ObtenerCorrelativoTabla("rp_djdireccion", 1);
		direccion.setDireccionId(Id);
		direccion.setEstado(Constante.ESTADO_ACTIVO);
		direccion.setFechaRegistro(DateUtil.getCurrentDate());
		direccion.setUsuarioId(getUser().getUsuarioId());
		direccion.setTerminal(getUser().getTerminal());
		getService().create(direccion);
		return Constante.RESULT_SUCCESS;
		//return getService().guardarRpDjdireccion(direccion);
	}

	//--
	public List<RpCondicionPropiedad> getAllRpCondicionPropiedad()
			throws Exception{
		return getService().getAllRpCondicionPropiedad();
	}

	public List<MpCondiEspePredio> getAllMpCondiEspePredio()
			throws Exception{
		return getService().getAllMpCondiEspePredio();
	}

	public MpPredio getMpPredio(Integer predioId) throws Exception{
		return getService().find(predioId, MpPredio.class);
		//return getService().getMpPredio(predioId);
	}

	public Integer guardarMpPredio(MpPredio mpPredio) throws Exception{
		Integer Id = getService().ObtenerCorrelativoTabla("mp_predio", 1);
		mpPredio.setPredioId(Id);
		mpPredio.setCodigoPredio(String.valueOf(Id));
		
		mpPredio.setFechaRegistro(DateUtil.getCurrentDate());
		mpPredio.setUsuarioId(getUser().getUsuarioId());
		mpPredio.setTerminal(getUser().getTerminal());
		
		getService().create(mpPredio);
		return Id;
	}

	public Integer actualizaMpPredio(MpPredio mpPredio) throws Exception{
		Integer result=Constante.RESULT_PENDING;
		mpPredio.setCodigoPredio(String.valueOf(mpPredio.getPredioId()));
		
		mpPredio.setFechaActualizacion(DateUtil.getCurrentDate());
		mpPredio.setFechaRegistro(DateUtil.getCurrentDate());
		
		mpPredio.setUsuarioId(getUser().getUsuarioId());
		mpPredio.setTerminal(getUser().getTerminal());
		
		getService().update(mpPredio);
		result=Constante.RESULT_SUCCESS;
		return result;
	}

	//PANTALLA DATOS DE LA CONSTRUCCION
	//Tipo Nivel
	//public List<RpTipoNivel> getAllRpTipoNivel()
	//Mes construccion
	public List<RjMes> getAllRjMes() throws Exception{
		return getService().getAllRjMes();
	}

	//Area comun contr. :
	//RjComponenteConstruccion
	public List<RjComponenteConstruccion> getAllRjComponenteConstruccion(
			int anioVigencia, int tipoComponente) throws Exception{
		return getService().getAllRjComponenteConstruccion(anioVigencia, tipoComponente);
	}

	//Area comun contr. :
	//rj_unidad_medida_acc
	public List<TgTabla> getAllTgTabla(String descripcion)
			throws Exception{
		return getService().getAllTgTabla(descripcion);
	}

	//Material Predominante : falta datos
	//rp_material_predominante
	//RpMaterialPredominante
	public List<RpMaterialPredominante> getAllRpMaterialPredominante()
			throws Exception{
		return getService().getAllRpMaterialPredominante();
	}

	//Estado de concervacion : falta datos
	//rj_estado_concervacion
	//RjEstadoConservacion
	public List<RjEstadoConservacion> getAllRjEstadoConservacion()
			throws Exception{
		return getService().getAllRjEstadoConservacion();
	}

	//Clasificacion depreciacion : falta datos
	//rj_tipo_depreciacion
	//RjTipoDepreciacion
	public List<RjTipoDepreciacion> getAllRjTipoDepreciacion()
			throws Exception{
		return getService().getAllRjTipoDepreciacion();
	}

	public ArrayList<RpDjconstruccion> getAllRpDjconstruccion(Integer dj_id,Integer periodo) throws Exception{
		return getService().getAllRpDjconstruccion(dj_id,periodo);
	}
	
	public ArrayList<RpDjconstruccion> getAllRpDjconstruccion2(Integer dj_id,Integer periodo) throws Exception{
		return getService().getAllRpDjconstruccion2(dj_id,periodo);
	}

	public int actualizaEstadoRpDjpredial(RpDjpredial rpDjpredial, String estado) throws Exception{
		Integer djIdAnno=getService().getUltimoRpDjpredial(rpDjpredial.getPredioId(), rpDjpredial.getAnnoDj(), rpDjpredial.getDjId(),rpDjpredial.getPersonaId());
		if(djIdAnno!=null&&djIdAnno>Constante.RESULT_PENDING){
			getService().actualizaEstadoRpDjpredial(djIdAnno, Constante.ESTADO_INACTIVO,Constante.FLAG_DJ_ANIO_INACTIVO );	
		}
		return Constante.RESULT_SUCCESS;
	}
	
	public String getFlagDjAnno(Integer dj, Integer periodo,
			Integer predioid) throws Exception{
		return getService().getFlagDjAnno(dj, periodo, predioid);
	}

	public int guardarRpDjpredial(RpDjpredial rpDjpredial)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		if(rpDjpredial.getDjId()!=null&&rpDjpredial.getDjId()>Constante.RESULT_PENDING){
			rpDjpredial.setUsuarioId(getUser().getUsuarioId());
			rpDjpredial.setTerminalRegistro(getUser().getTerminal());
			rpDjpredial.setTerminal(getUser().getTerminal());
			rpDjpredial.setFechaActualizacion(DateUtil.getCurrentDate());
			getService().update(rpDjpredial);
			result=rpDjpredial.getDjId();
		}else{
			Integer DjId = getService().ObtenerCorrelativoTabla("rp_djpredial", 1);
			rpDjpredial.setDjId(DjId);
			rpDjpredial.setUsuarioId(getUser().getUsuarioId());
			rpDjpredial.setTerminalRegistro(getUser().getTerminal());
			rpDjpredial.setTerminal(getUser().getTerminal());
			rpDjpredial.setFechaRegistro(DateUtil.getCurrentDate());
			getService().create(rpDjpredial);
			result=DjId;
		}
		return result;
	}
	
	public int actualizaRpDjpredial(RpDjpredial rpDjpredial)throws Exception{
		rpDjpredial.setFechaActualizacion(DateUtil.getCurrentDate());
		rpDjpredial.setFechaRegistro(DateUtil.getCurrentDate());
		
		rpDjpredial.setTerminal(getUser().getTerminal());
		rpDjpredial.setTerminalRegistro(getUser().getTerminal());
		
		rpDjpredial.setUsuarioId(getUser().getUsuarioId());
		
		getService().update(rpDjpredial);
		return Constante.RESULT_SUCCESS;
	}

	public RpDjconstruccion getRpDjconstruccion(Integer djId,Integer construccionId,Integer periodo) throws Exception{
		return getService().getRpDjconstruccion(djId, construccionId,periodo);
	}

	public List<GnTipoDenoUrbana> getAllGnTipoDenoUrbana()
			throws Exception{
		return getService().getAllGnTipoDenoUrbana();
	}

	public RpDjpredial getRpDjpredial(Integer djId)
			throws Exception{
		return getService().find(djId, RpDjpredial.class);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<GnVia> findGnVia(Integer tipo_via_id)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_tipo_via_id", tipo_via_id);
		return (ArrayList<GnVia>)(List)getService().findByNamedQuery("getAllGnViaByTipoViaId", parameters);
	}

	public ArrayList<GnLugar> findGnLugar(Integer sector_id)throws Exception{
		return getService().findGnLugar(sector_id);
	}
	
	public List<RpTipoTierraRustico> getAllRpTipoTierraRustico()
			throws Exception{
		return getService().getAllRpTipoTierraRustico();
	}

	public List<RpAltitud> getAllRpAltitud() throws Exception{
		return getService().getAllRpAltitud();
	}

	public List<RpCategoriaRustico> getAllRpCategoriaRustico()
			throws Exception{
		return getService().getAllRpCategoriaRustico();
	}

	public int existDjDireccion(Integer dj_id) throws Exception{
		return getService().existDjDireccion(dj_id);
	}

	public int getDjDireccionActive(Integer dj_id) throws Exception{
		return getService().getDjDireccionActive(dj_id);
	}

	public int desactiveDirecciones(Integer dj_id, Integer direccion_id)
			throws Exception{
		return getService().desactiveDirecciones(dj_id, direccion_id);
	}

	public int existDjConstruccion(Integer dj_id) throws Exception{
		return getService().existDjConstruccion(dj_id);
	}

	public int getUltimoConstruccionId(Integer dj_id) throws Exception{
		return getService().getUltimoConstruccionId(dj_id);
	}

	public RpDjdireccion getRpDjDireccion(Integer DjId)
			throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_dj_id", DjId);
		List<Object> lista=getService().findByNamedQuery("getAllRpDjdireccionByDjId", parameters);
		if(lista!=null&&lista.size()>0){
			return (RpDjdireccion)lista.get(0);
		}
		return null;
		//return getService().getRpDjDireccion(DjId);
	}

	public GnVia getGnVia(Integer ViaId) throws Exception{
		return getService().getGnVia(ViaId);
	}

	public GnDenominacionUrbana getGnDenominacionUrbana(Integer DenoId)
			throws Exception{
		return getService().find(DenoId, GnDenominacionUrbana.class);
		//return getService().getGnDenominacionUrbana(DenoId);
	}

	public String ConcatenarDomicilioUrbano(RpDjdireccion direccion,
			HashMap<Integer, String> mapIGnTipoEdificacion,
			HashMap<Integer, String> mapIGnTipoInterior,
			HashMap<Integer, String> mapIGnTipoIngreso,
			HashMap<Integer, String> mapIGnTipoAgrupamiento,
			HashMap<Integer, String> mapIGnTipoVia,
			HashMap<Integer, String> mapIGnTipoDenUrbana,String denominacionSector,Integer numeroCuadra){
		return getService().ConcatenarDomicilioUrbano(direccion, mapIGnTipoEdificacion, mapIGnTipoInterior, mapIGnTipoIngreso, mapIGnTipoAgrupamiento, mapIGnTipoVia, mapIGnTipoDenUrbana,denominacionSector,numeroCuadra);
	}

	public ArrayList<ListRpDjPredial> getListRpDjpredial(Integer predioId,Integer personaId) throws Exception{
		return getService().getListRpDjpredial(predioId,personaId);
	}
	
	public ArrayList<ListRpDjPredial> getListRpDjpredialTodos(Integer predioId,Integer personaId) throws Exception{
		return getService().getListRpDjpredialTodos(predioId, personaId);
	}

	public FindPersona getFindPersona(Integer personaId)
			throws Exception{
		return getService().getFindPersona(personaId);
	}

	public ArrayList<FindPersona> getFindPersona() throws Exception{
		return getService().getFindPersona();
	}

	public ArrayList<FindPersona> getFindEmpresas() throws Exception{
		return getService().getFindEmpresas();
	}

	public Integer getPersonaId(Integer tipDoc, String numeroDoc)
			throws Exception{
		return getService().getPersonaId(tipDoc, numeroDoc);
	}

	
	public int guardarRpDjconstruccion(RpDjconstruccion rpDjconstruccion) throws Exception{
		Integer Id=getService().ObtenerCorrelativoTabla("rp_djconstruccion", 1);
		rpDjconstruccion.setConstruccionId(Id);
		rpDjconstruccion.setEstado(Constante.ESTADO_ACTIVO);
		
		rpDjconstruccion.setFechaRegistro(DateUtil.getCurrentDate());
		rpDjconstruccion.setTerminal(getUser().getTerminal());
		rpDjconstruccion.setUsuarioId(getUser().getUsuarioId());
		
		getService().create(rpDjconstruccion);
		return Constante.RESULT_SUCCESS;
		//return getService().guardarRpDjconstruccion(rpDjconstruccion);
	}

	public int actualizaRpDjconstruccion(RpDjconstruccion rpDjconstruccion) throws Exception{
		rpDjconstruccion.setEstado(Constante.ESTADO_ACTIVO);
		
		rpDjconstruccion.setFechaRegistro(DateUtil.getCurrentDate());
		rpDjconstruccion.setTerminal(getUser().getTerminal());
		rpDjconstruccion.setUsuarioId(getUser().getUsuarioId());
		
		getService().update(rpDjconstruccion);
		return Constante.RESULT_SUCCESS;
		//return getService().actualizaRpDjconstruccion(rpDjconstruccion);
	}

	public FindDireccion verificaDireccion(Integer PredioId,
			Integer idVia, String numero1, String letra1, String numero2,
			String letra2,Integer edificacionId,String edificacion,Integer interiorId,String interior,String referencia) throws Exception{
		return getService().verificaDireccion(PredioId, idVia, numero1, letra1, numero2, letra2,edificacionId,edificacion,interiorId,interior,referencia);
	}

	//--
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<RpTipoObra> getAllRpTipoObra(Integer categoriaObraId,
			Integer codMinVivienda) throws Exception {
		// return getService().getAllRpTipoObra(categoriaObraId);
		String nameQuery;
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (codMinVivienda == 0) {
			nameQuery = "getAllTipoObra";
		} else {
			parameters.put("p_idMinVivienda", codMinVivienda);
			nameQuery = "getAllTipoObraIdMinVivienda";
		}	
		parameters.put("p_categoriaObraId", categoriaObraId);
		
		return (ArrayList<RpTipoObra>) (List) getService().findByNamedQuery(nameQuery, parameters);
	}

	public ArrayList<RpCategoriaObra> getAllRpCategoriaObra(Integer periodo) throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		return (ArrayList<RpCategoriaObra>)(List)getService().findByNamedQuery("getAllRpCategoriaObra", parameters);
	}
	
	public RpInstalacionDj getRpInstalacionDj(int djId,
			int instalacionId) throws Exception{
		return getService().getRpInstalacionDj(djId, instalacionId);
	}

	public RpOtrosFrente getRpOtrosFrente(int djId, int otroFrenteId)
			throws Exception{
		return getService().getRpOtrosFrente(djId, otroFrenteId);
	}

	public RpDjuso getRpDjuso(int arbitrioId, int usoId)
			throws Exception{
		return getService().getRpDjuso(arbitrioId, usoId);
	}

	public RjDocuAnexo getRjDocuAnexo(int DjId, int docuAnexoId)
			throws Exception{
		return getService().getRjDocuAnexo(DjId, docuAnexoId);
	}

	public int actualizarRpInstalacionDj(RpInstalacionDj rpInstalacionDj) throws Exception{
		rpInstalacionDj.setFechaRegistro(DateUtil.getCurrentDate());
		rpInstalacionDj.setTerminal(getUser().getTerminal());
		rpInstalacionDj.setUsuarioId(getUser().getUsuarioId());
		
		getService().update(rpInstalacionDj);
		return Constante.RESULT_SUCCESS;
	}

	public int guardarRpInstalacionDj(RpInstalacionDj rpInstalacionDj)throws Exception{
		Integer Id=getService().obtenerCorrelativoTabla("rp_instalacion_dj");
		rpInstalacionDj.setInstalacionId(Id);
		rpInstalacionDj.setFechaRegistro(DateUtil.getCurrentDate());
		rpInstalacionDj.setTerminal(getUser().getTerminal());
		rpInstalacionDj.setUsuarioId(getUser().getUsuarioId());
		getService().create(rpInstalacionDj);
		return Constante.RESULT_SUCCESS;
	}

	public ArrayList<RpInstalacionDj> getAllRpInstalacionDj(int djId)
			throws Exception{
		return getService().getAllRpInstalacionDj(djId);
	}

	public int guardarRpOtrosFrente(RpOtrosFrente rpOtrosFrente)
			throws Exception{
		
		rpOtrosFrente.setUsuarioId(getUser().getUsuarioId());
		rpOtrosFrente.setFechaRegistro(DateUtil.getCurrentDate());
		rpOtrosFrente.setTerminal(getUser().getTerminal());
	
		return getService().guardarRpOtrosFrente(rpOtrosFrente);
	}

	public ArrayList<RpOtrosFrente> getAllRpOtrosFrente(int djId)
			throws Exception{
		return getService().getAllRpOtrosFrente(djId);
	}

	public List<RpTipoUso> getAllRpTipoUso() throws Exception{
		return getService().getAllRpTipoUso();
	}
	
	public List<RpTipoUso> getAllRpTipoUsos(int anio) throws Exception{
		return getService().getAllRpTipoUsos(anio);
	}
	
	public List<RpTipoUso> getAllRpTipoUsoSinTerreno(int anio) throws Exception{
		return getService().getAllRpTipoUsoSinTerreno(anio);
	}

	public int actualizaAreaUsoRpDjuso(RpDjuso uso) throws Exception{
		return getService().actualizaAreaUsoRpDjuso(uso);
	}

	public ArrayList<RpDjuso> getAllRpDjuso(int arbitrioId)
			throws Exception{
		return getService().getAllRpDjuso(arbitrioId);
	}

	public ArrayList<RjDocuAnexo> getAllRjDocuAnexo(int djId)
			throws Exception{
		return getService().getAllRjDocuAnexo(djId);
	}
	
	public ArrayList<RelacionadosDTO> getAllRelacionadoDTO(Integer personaId)
			throws Exception{
		//System.out.println("bo getAllRelacionadoDTO");
		return getService().getAllRelacionados(personaId);
	}

	public int guardarRjDocuAnexo(RjDocuAnexo rjDocuAnexo)
			throws Exception{
		
		Integer Id=getService().obtenerCorrelativoTabla("rj_docu_anexo");
		rjDocuAnexo.setDocuAnexoId(Id);
		rjDocuAnexo.setUsuarioId(getUser().getUsuarioId());
		rjDocuAnexo.setFechaRegistro(DateUtil.getCurrentDate());
		rjDocuAnexo.setTerminal(getUser().getTerminal());
		
		getService().create(rjDocuAnexo);
		
		return Constante.RESULT_SUCCESS;
	}

	public int deleteRjDocuAnexo(int djId, int docuAnexoId)
			throws Exception{
		return getService().deleteRjDocuAnexo(djId, docuAnexoId);
	}

	public int deleteRpDjconstruccion(int djId, int construccionId)
			throws Exception{
		return getService().deleteRpDjconstruccion(djId, construccionId);
	}

	public int deleteRpDjuso(int djarbitrioId, int djusoId)
			throws Exception{
		return getService().deleteRpDjuso(djarbitrioId, djusoId);
	}

	public int deleteRpOtrosFrente(int djId, int otroFrenteId)
			throws Exception{
		return getService().deleteRpOtrosFrente(djId, otroFrenteId);
	}

	public int deleteRpInstalacionDj(int djId, int instalacionId)
			throws Exception{
		return getService().deleteRpInstalacionDj(djId, instalacionId);
	}

	public Integer getDjArbitrioId(int djId) throws Exception{
		return getService().getDjArbitrioId(djId);
	}

	public int guardarDjArbitrioId(RpDjarbitrio rpDjarbitrio)throws Exception{
		Integer Id=getService().ObtenerCorrelativoTabla("rp_djarbitrios", 1);
		rpDjarbitrio.setDjarbitrioId(Id);
		rpDjarbitrio.setFechaRegistro(DateUtil.getCurrentDate());
		rpDjarbitrio.setTerminal(getUser().getTerminal());
		rpDjarbitrio.setUsuarioId(getUser().getUsuarioId());
		//return getService().guardarDjArbitrioId(rpDjarbitrio);
		getService().create(rpDjarbitrio);
		return Constante.RESULT_SUCCESS;
	}
	
	//--
	public int guardarRpDjuso(RpDjuso rpDjuso) throws Exception{
		Integer Id=getService().ObtenerCorrelativoTabla("rp_djuso", 1);
		rpDjuso.setDjusoId(Id);
		rpDjuso.setUsuarioId(getUser().getUsuarioId());
		rpDjuso.setFechaRegistro(DateUtil.getCurrentDate());
		rpDjuso.setTerminal(getUser().getTerminal());
		getService().create(rpDjuso);
		return Constante.RESULT_SUCCESS;
		//return getService().guardarRpDjuso(rpDjuso);
	}

	public int getUltimoDjUsoId(Integer djarbitrio_id)
			throws Exception{
		return getService().getUltimoDjUsoId(djarbitrio_id);
	}

	public int actualizaRpDjuso(RpDjuso rpDjuso) throws Exception{
		int result=Constante.RESULT_PENDING;
		try {
			rpDjuso.setUsuarioId(getUser().getUsuarioId());
			rpDjuso.setFechaRegistro(DateUtil.getCurrentDate());
			rpDjuso.setTerminal(getUser().getTerminal());
			
			getService().update(rpDjuso);
			
			result=Constante.RESULT_SUCCESS;
			return result;
				
		} catch (Exception e) {
			e.printStackTrace();
			result=Constante.RESULT_FAILED;
			return result;
		}
	}

	public int deleteRpDjUsoDetalle(int rpDjusoId) throws Exception{
		return getService().deleteRpDjUsoDetalle(rpDjusoId);
	}

	public int guardarRpDjusoDetalle(RpDjusoDetalle detalle)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		if(detalle.getDjusoDetalleId().equals(Constante.RESULT_PENDING)){
			//cramirez
			//Integer Id=getService().ObtenerCorrelativoTabla("rp_transferencia_propiedad", 1);
			Integer Id=getService().ObtenerCorrelativoTabla("rp_djuso_detalle", 1);
			detalle.setDjusoDetalleId(Id);
			
			detalle.setFechaRegistro(DateUtil.getCurrentDate());
        	detalle.setTerminal(getUser().getTerminal());
        	detalle.setUsuarioId(getUser().getUsuarioId());
			
			getService().create(detalle);
			result=Id;
		}
		//return getService().guardarRpDjusoDetalle(detalle);
		return result;
	}

	public RpDjusoDetalle getRpDjusoDetalle(int dJUsoId,
			int construccionId) throws Exception{
		return getService().getRpDjusoDetalle(dJUsoId, construccionId);
	}

	public ArrayList<RpDjusoDetalle> getAllRpDjusoDetalle(int dJUsoId)
			throws Exception{
		return getService().getAllRpDjusoDetalle(dJUsoId);
	}

	public BigDecimal getAreaUsada(int djarbitrioId, int construccionId)
			throws Exception{
		return getService().getAreaUsada(djarbitrioId, construccionId);
	}

	public BigDecimal getAreaUsadaRecalculo(int djusoId,
			int djarbitrioId, int construccionId) throws Exception{
		return getService().getAreaUsadaRecalculo(djusoId, djarbitrioId, construccionId);
	}
	
	public Integer registrarAdquirientes(List<BuscarPersonaDTO> lstTransferentes,Integer NewDjId,String tipoTransferencia)throws Exception{
		getService().desactiveTransferentes(NewDjId);
		for(BuscarPersonaDTO persona:lstTransferentes){
			RpTransferenciaPropiedad adquiriente=new RpTransferenciaPropiedad();
			Integer Id=getService().ObtenerCorrelativoTabla("rp_transferencia_propiedad", 1);
			adquiriente.setTransferenciaId(Id);
			adquiriente.setDjId(NewDjId);
			adquiriente.setPersonaId(persona.getPersonaId());
			adquiriente.setTipo(tipoTransferencia);
			adquiriente.setEstado(Constante.ESTADO_ACTIVO);
						
			adquiriente.setArea(persona.getArea());
			adquiriente.setAreaMatriz(persona.getAreaMatriz());
			adquiriente.setAreaTransferida(persona.getAreaTransferida());
			adquiriente.setAreaRestante(persona.getAreaRestante());
			
			adquiriente.setPorcentaje(BigDecimal.valueOf(persona.getPorcentaje().doubleValue()));			
			adquiriente.setPorcentajeMatriz(persona.getPorcentajeMatriz());
			adquiriente.setPorcentajeTransferido(persona.getPorcentajeTransferido());
			adquiriente.setPorcentajeRestante(persona.getPorcentajeRestante());
			
			adquiriente.setFormaAdquisicion(persona.getFormaAdquisicion());
			
			adquiriente.setDescargoAuto(persona.getDescargoAutomatico());			
			
			adquiriente.setFechaActualizacion(DateUtil.getCurrentDate());
			adquiriente.setFechaRegistro(DateUtil.getCurrentDate());
			adquiriente.setTerminal(getUser().getTerminal());
			adquiriente.setUsuarioId(getUser().getUsuarioId());
			
			getService().create(adquiriente);
		}
		return 1;
	}
	
	public Integer eliminaAdquirientes(Integer DjId)throws Exception{
		Integer result=getService().desactiveTransferentes(DjId);
		return result;
	}
	
	public Integer registrarDocAnexos(List<AnexosDeclaVehicDTO> lstAnexos,Integer NewDjId)throws Exception{
		for(AnexosDeclaVehicDTO docanexo:lstAnexos){
			RpSustentoPredial docsustento=new RpSustentoPredial();
			Integer Id=getService().ObtenerCorrelativoTabla("rp_sustento_predial", 1);
			docsustento.setSustentoId(Id);
			docsustento.setDjId(NewDjId);
			docsustento.setNroDocumento(docanexo.getGlosaDoc());
			docsustento.setDocSustentatorioId(docanexo.getDocSustentatorioId());
			
			docsustento.setFechaRegistro(DateUtil.getCurrentDate());
			docsustento.setTerminal(getUser().getTerminal());
			docsustento.setUsuarioId(getUser().getUsuarioId());
			
			getService().create(docsustento);
		}
		return 1;
	}
	
	public boolean cambiarEstadoDjv(int djId,String estado,String flagDjAnno)throws Exception {
		return getService().cambiarEstadoDjv(djId, estado,flagDjAnno);
	}
	
	public List<BuscarPersonaDTO> getTransferentePropiedad(Integer djId,String TipoTransferencia)throws Exception {
		return getService().getTransferentePropiedad(djId,TipoTransferencia);
	}
	public List<AnexosDeclaVehicDTO> getDocumentosAnexos(Integer djId)throws Exception {
		return getService().getDocumentosAnexos(djId);
	}
	
	public Double getValorArancel(Integer ubicacionId,Integer periodo)throws Exception {
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_ubicacion_id", ubicacionId);
		parameters.put("p_periodo", periodo);
		List<Object> lArancel=getService().findByNamedQuery("getDtArancelUbicacionByPeriodo", parameters);
		Double valorArancel=Double.valueOf(0);
		
		if(lArancel!=null&&lArancel.size()>0){
		   DtArancelUbicacion arancel=(DtArancelUbicacion)lArancel.get(0);
		   valorArancel=arancel.getValorArancel();
		}
		return valorArancel;
	}
	
	public GnUbicacion getGnUbicacion(Integer ubicacionId)throws Exception{
		return getService().find(ubicacionId, GnUbicacion.class);
	}
	
	//caltamirano:ini
	public boolean updateDjPredial(int djId, Integer djAnteriorId, String fiscalizado, String fiscaAceptada, String fiscaCerrada){
		return getService().updateDjPredial(djId, djAnteriorId, fiscalizado, fiscaAceptada, fiscaCerrada);
	}
	
	public boolean isDjpAtYear(int year, int personaId, int predioId){
		return getService().isDjpAtYear(year, personaId, predioId);
	}
	
	public RpDjpredial getDjpActivoAnio(int personId, int predioId, int year){
		return getService().getDjpActivoAnio(personId,predioId,year);
	}
	//caltamirano:fin

    
    /**
	 * Valida que para hacer una copia de una dj predial a otro año, este afecto al contribuyente.
	 * 
	 * @param djpId Identificador de la declaración jurada predial.
	 * @param anioCrear Año al que se desea hacer la copia.
	 * @return Verdadero o falso dependiendo si el contribuyente esta afecto o no.
	 */
	public boolean estaEnRangoAfecContrib(int djpId, int anioCrear){
		return getService().estaEnRangoAfecContrib(djpId, anioCrear);
	}
	
	public ArrayList<MpTipoDocuIdentidad> getAllMpTipoDocuIdentidad()throws Exception {
		return (ArrayList<MpTipoDocuIdentidad>)(List)getService().findByNamedQuery("getAllMpTipoDocuIdentidad");
	}
	
	public List<Object> getAllRpTipoUsoPredioRustico()throws Exception {
		return getService().findByNamedQuery("getAllRpTipoUsoPredioRustico");
	}
	
	public RpDjpredial getAllRpDjpredialByAnnoDj(Integer personaId,Integer predioId,Integer annoDj)throws Exception {
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_predio_id", predioId);
		parameters.put("p_anno_dj", annoDj);
		List<Object> lRpDjPredial=getService().findByNamedQuery("getAllRpDjpredialByAnnoDjPersonaId",parameters);
		if(lRpDjPredial!=null&&lRpDjPredial.size()>0){
			return (RpDjpredial)lRpDjPredial.get(0);
		}
		return null;
	}	
	
	@Override
	public List<GnVia> getAllGnVia(Integer tipoViaId) throws SisatException {
		
		String query = "from GnVia v ";
		
		if(tipoViaId != null){
			query = query.concat("where v.estado=1 and v.tipoViaId = ").concat(tipoViaId.toString());
		}
		
		Query q = em.createQuery(query);
		
		List<GnVia> lstGnVia = q.getResultList();
		
		
		return lstGnVia;
	}

	@Override
	public List<GnTipoVia> getAllGnTipoVia(Integer gnViaId) throws SisatException {

		String query = "from GnTipoVia tv where tv.estado='1'";

		if (gnViaId != null) {
			query = query.concat("inner join GnVia v where v.viaId = ").concat(gnViaId.toString());
		}

		Query q = em.createQuery(query);

		List<GnTipoVia> lstGnTipoVia = q.getResultList();

		return lstGnTipoVia;
	}

	@Override
	public ArrayList<UbicacionDTO> findGnViaV2(Integer tipoViaId, Integer viaId, String descripcion)
			throws SisatException {

		return getService().findGnViaV2(tipoViaId, viaId, descripcion);
	}
	
	public ArrayList<RpTransferenciaPropiedad> getAllRpTransferenciaPropiedad(Integer djId)throws Exception {
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_dj_id", djId);
		return (ArrayList<RpTransferenciaPropiedad>)(List)getService().findByNamedQuery("getAllTransferente",parameters);
	}
	
	public RpCategoriaObra getRpCategoriaObraById(Integer categoriaObraId)throws Exception {
		return getService().find(categoriaObraId,RpCategoriaObra.class);
	}
	
	public List<FindRpTipoObraDTO> getAllRpCaregoriaObraXIdMV(Integer idAnteriorTO)throws Exception {
		return service.getAllRpCaregoriaObraXIdMV(idAnteriorTO);
	}

	public RpTipoObra getRpTipoObra(Integer tipoObraId)throws Exception {
		return getService().find(tipoObraId, RpTipoObra.class);		
	}	
	
	public void eliminarDJPredio(ListRpDjPredial djpredial, int personaId)throws SisatException{		
		getService().eliminarDJPredio(djpredial, personaId,  this.getUser().getUsuarioId(), this.getUser().getTerminal() );
		
	}
	
	public void inactivarDJPredio(ListRpDjPredial djpredial, int personaId)throws SisatException{		
		getService().inactivarDJPredio(djpredial, personaId,  this.getUser().getUsuarioId(), this.getUser().getTerminal() );
		
	}
	
	public ArrayList<UbicacionDTO> findGnUbicacion(Integer tipoViaId, Integer viaId, Integer sectorId, Integer lugarId)	throws SisatException{
		return getService().findGnUbicacion(tipoViaId, viaId, sectorId, lugarId);		
	}
	
	public void agregarUbicacion(UbicacionDTO nuevaUbicacion) throws SisatException {	
		
		Integer id=getService().obtenerCorrelativoTabla("gn_ubicacion");
		nuevaUbicacion.setUbicacionId(id);
		
		getService().agregarUbicacion(nuevaUbicacion, this.getUser().getUsuarioId(), this.getUser().getTerminal());		
	}

	public void agregarArancelUbicacion(DtArancelUbicacion arancelUbicacion)
			throws SisatException {
		// buscar ubicacion

		if (arancelUbicacion.getArancelUbicacionId() == null) {
			/** Caso nuevo */
			Integer id = getService().obtenerCorrelativoTabla(
					"dt_arancel_ubicacion");

			arancelUbicacion.setArancelUbicacionId(id);
			arancelUbicacion.setUsuarioId(getUser().getUsuarioId());
			arancelUbicacion.setFechaRegistro(DateUtil.getCurrentDate());
			arancelUbicacion.setEstado(Constante.ESTADO_ACTIVO);
			arancelUbicacion.setTerminal(getUser().getTerminal());
			em.persist(arancelUbicacion);
		} else {
			/** Caso actualizacion */
			arancelUbicacion.setUsuarioId(getUser().getUsuarioId());
			arancelUbicacion.setFechaRegistro(DateUtil.getCurrentDate());
			// arancelUbicacion.setFechaActualizacion(DateUtil.getCurrentDate());
			arancelUbicacion.setEstado(Constante.ESTADO_ACTIVO);
			arancelUbicacion.setTerminal(getUser().getTerminal());
			em.merge(arancelUbicacion);
		}

		
	}

	public void agregarFrecuenciaRecojo(DtFrecuenciaRecojo frecuenciaRecojo)
			throws SisatException {

		if (frecuenciaRecojo.getFrecuenciaRecojoId() == null) {
			Integer id = getService().obtenerCorrelativoTabla(
					"dt_frecuencia_recojo");

			frecuenciaRecojo.setFrecuenciaRecojoId(id);
			frecuenciaRecojo.setEstado(Constante.ESTADO_ACTIVO);
			frecuenciaRecojo.setUsuarioId(this.getUser().getUsuarioId());
			frecuenciaRecojo.setFechaRegistro(DateUtil.getCurrentDate());
			frecuenciaRecojo.setTerminal(this.getUser().getTerminal());
			em.persist(frecuenciaRecojo);
		} else {

			frecuenciaRecojo.setEstado(Constante.ESTADO_ACTIVO);
			frecuenciaRecojo.setUsuarioId(this.getUser().getUsuarioId());
			// frecuenciaRecojo.setFechaACtualizacion(DateUtil.getCurrentDate());
			frecuenciaRecojo.setFechaRegistro(DateUtil.getCurrentDate());
			frecuenciaRecojo.setTerminal(this.getUser().getTerminal());
			em.merge(frecuenciaRecojo);
		}

		
	}

	public void agregarFrecuenciaLimpieza(
			DtFrecuenciaLimpieza frecuenciaLimpieza) throws SisatException {

		if (frecuenciaLimpieza.getFrecuenciaLimpiezaId() == null) {
			Integer id = getService().obtenerCorrelativoTabla(
					"dt_frecuencia_limpieza");

			frecuenciaLimpieza.setFrecuenciaLimpiezaId(id);
			frecuenciaLimpieza.setEstado(Constante.ESTADO_ACTIVO);
			frecuenciaLimpieza.setUsuarioId(this.getUser().getUsuarioId());
			frecuenciaLimpieza.setFechaRegistro(DateUtil.getCurrentDate());
			frecuenciaLimpieza.setTerminal(this.getUser().getTerminal());
			em.persist(frecuenciaLimpieza);
		} else {

			frecuenciaLimpieza.setEstado(Constante.ESTADO_ACTIVO);
			frecuenciaLimpieza.setUsuarioId(this.getUser().getUsuarioId());
			// frecuenciaLimpieza.setFechaActualizacion(DateUtil.getCurrentDate());
			frecuenciaLimpieza.setFechaRegistro(DateUtil.getCurrentDate());
			frecuenciaLimpieza.setTerminal(this.getUser().getTerminal());
			em.merge(frecuenciaLimpieza);
		}
		
	}

	public void agregarCercaniaParque(DtCercaniaParque cercaniaParque)
			throws SisatException {

		if (cercaniaParque.getCercaniaParquesId() == null) {
			Integer id = getService().obtenerCorrelativoTabla(
					"dt_cercania_parques");

			cercaniaParque.setCercaniaParquesId(id);
			cercaniaParque.setEstado(Constante.ESTADO_ACTIVO);
			cercaniaParque.setUsuarioId(this.getUser().getUsuarioId());
			cercaniaParque.setFechaRegistro(DateUtil.getCurrentDate());
			cercaniaParque.setTerminal(this.getUser().getTerminal());
			em.persist(cercaniaParque);
		} else {

			cercaniaParque.setEstado(Constante.ESTADO_ACTIVO);
			cercaniaParque.setUsuarioId(this.getUser().getUsuarioId());
			// cercaniaParque.setFechaActualizacion(DateUtil.getCurrentDate());
			cercaniaParque.setFechaRegistro(DateUtil.getCurrentDate());
			cercaniaParque.setTerminal(this.getUser().getTerminal());
			em.merge(cercaniaParque);
		}
		
	}

	public void agregarZonaSeguridadUbicacion(
			DtZonaSeguridadUbicacion zonaSeguridadUbicacion)
			throws SisatException {
		if (zonaSeguridadUbicacion.getZonaSeguridadUbicaId() == null) {
			Integer id = getService().obtenerCorrelativoTabla(
					"dt_zona_seguridad_ubicacion");

			zonaSeguridadUbicacion.setZonaSeguridadUbicaId(id);
			zonaSeguridadUbicacion.setEstado(Constante.ESTADO_ACTIVO);
			zonaSeguridadUbicacion.setUsuarioId(this.getUser().getUsuarioId());
			zonaSeguridadUbicacion.setFechaRegistro(DateUtil.getCurrentDate());
			zonaSeguridadUbicacion.setTerminal(this.getUser().getTerminal());
			em.persist(zonaSeguridadUbicacion);
		} else {
			zonaSeguridadUbicacion.setEstado(Constante.ESTADO_ACTIVO);
			zonaSeguridadUbicacion.setUsuarioId(this.getUser().getUsuarioId());
			// zonaSeguridadUbicacion.setFechaActualizacion(DateUtil.getCurrentDate());
			zonaSeguridadUbicacion.setFechaRegistro(DateUtil.getCurrentDate());
			zonaSeguridadUbicacion.setTerminal(this.getUser().getTerminal());
			em.merge(zonaSeguridadUbicacion);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<DtGrupoCercania> getAllDtGrupoCercania(Integer periodo) throws SisatException{
		List<DtGrupoCercania> lstGrupoCercania = new ArrayList<DtGrupoCercania>();
		try {
			Query q = em
					.createQuery("from DtGrupoCercania d where d.periodo = :periodo and d.estado = '1'");
			q.setParameter("periodo", periodo);

			lstGrupoCercania = q.getResultList();
		} catch (NoResultException e) {			
		}catch (Exception e) {
			throw new SisatException(e.getMessage());
		}
		
		return lstGrupoCercania;
	}

	@SuppressWarnings("unchecked")
	public List<DtZonaSeguridad> getAllDtZonaSeguridad(Integer periodo) throws SisatException{
		List<DtZonaSeguridad> lstZonaSeguridad = new ArrayList<DtZonaSeguridad>();
		
		try {
			Query q = em
					.createQuery("from DtZonaSeguridad d where d.periodo = :periodo and d.estado = '1'");
			q.setParameter("periodo", periodo);

			lstZonaSeguridad = q.getResultList();
		} catch (NoResultException e) {			
		}catch (Exception e) {
			throw new SisatException(e.getMessage());
		}
		
		return lstZonaSeguridad;
	}
	
	public DtArancelUbicacion getDtArancelUbicacion(Integer ubicacionId,
			Integer periodo) throws SisatException {

		DtArancelUbicacion dtArancelUbicacion = new DtArancelUbicacion();
		try {

			Query q = em
					.createQuery("from DtArancelUbicacion d where d.ubicacionId = :ubicacionId and d.periodo = :periodo and d.estado = '1'");
			q.setParameter("ubicacionId", ubicacionId);
			q.setParameter("periodo", periodo);

			dtArancelUbicacion = (DtArancelUbicacion) q.getSingleResult();

		}catch (NoResultException e) {			
		} 		
		catch (Exception e) {
			throw new SisatException("Error en la tabla dt_arancel_ubicacion. ".concat(e.getMessage()));
		}
		return dtArancelUbicacion;

	}

	public DtFrecuenciaRecojo getDtFrecuenciaRecojo(Integer ubicacionId,
			Integer periodo) throws SisatException {

		DtFrecuenciaRecojo dtFrecuenciaRecojo = new DtFrecuenciaRecojo();
		try {
			Query q = em
					.createQuery("from DtFrecuenciaRecojo d where d.ubicacionId = :ubicacionId and d.periodo = :periodo and d.estado = '1'");
			q.setParameter("ubicacionId", ubicacionId);
			q.setParameter("periodo", periodo);

			dtFrecuenciaRecojo = (DtFrecuenciaRecojo) q.getSingleResult();
		}catch (NoResultException e) {			
		}		
		catch (Exception e) {
			throw new SisatException("Error en la tabla dt_frecuencia_recojo. ".concat(e.getMessage()));
		}
		return dtFrecuenciaRecojo;
	}
	
	public DtFrecuenciaLimpieza getDtFrecuenciaLimpieza(Integer ubicacionId, Integer periodo) throws SisatException {

		DtFrecuenciaLimpieza dtFrecuenciaLimpieza = new DtFrecuenciaLimpieza();
		try {
			Query q = em
					.createQuery("from DtFrecuenciaLimpieza d where d.ubicacionId = :ubicacionId and d.periodo = :periodo and d.estado = '1'");
			q.setParameter("ubicacionId", ubicacionId);
			q.setParameter("periodo", periodo);

			dtFrecuenciaLimpieza = (DtFrecuenciaLimpieza) q.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			throw new SisatException("Error en la tabla dt_frecuencia_limpieza. ".concat(e.getMessage()));
		}

		return dtFrecuenciaLimpieza;
	}

	public DtCercaniaParque getDtCercaniaParque(Integer ubicacionId,
			Integer periodo) throws SisatException {

		DtCercaniaParque dtCercaniaParque = new DtCercaniaParque();
		try {
			Query q = em
					.createQuery("from DtCercaniaParque d where d.ubicacionId = :ubicacionId and d.periodo = :periodo and d.estado = '1'");
			q.setParameter("ubicacionId", ubicacionId);
			q.setParameter("periodo", periodo);

			dtCercaniaParque = (DtCercaniaParque) q.getSingleResult();
		} catch (NoResultException e) {			
		}catch (Exception e) {
			throw new SisatException("Error en la tabla dt_cercania_parque. ".concat(e.getMessage()));
		}
		return dtCercaniaParque;

	}

	public DtZonaSeguridadUbicacion getDtZonaSeguridadUbicacion(
			Integer ubicacionId, Integer periodo) throws SisatException {
		DtZonaSeguridadUbicacion dtZonaSeguridadUbicacion = new DtZonaSeguridadUbicacion();
		try {
			Query q = em
					.createQuery("from DtZonaSeguridadUbicacion d where d.ubicacionId = :ubicacionId and d.periodo = :periodo and d.estado = '1'");
			q.setParameter("ubicacionId", ubicacionId);
			q.setParameter("periodo", periodo);

			dtZonaSeguridadUbicacion = (DtZonaSeguridadUbicacion) q
					.getSingleResult();
		} catch (NoResultException e) {			
		} catch (Exception e) {
			throw new SisatException("Error en la tabla dt_zona_seguridad_ubicacion. ".concat(e.getMessage()));
		}
		return dtZonaSeguridadUbicacion;
	}
	
	public RpTransferenciaPropiedad getRpTransferenciaPropiedad(int djId) throws SisatException{
		RpTransferenciaPropiedad rpTransferenciaPropiedad = new RpTransferenciaPropiedad();
		List<RpTransferenciaPropiedad> lstRpTransferenciaPropiedad = new ArrayList<RpTransferenciaPropiedad>();
		try{
			Query q = em
					.createQuery("from RpTransferenciaPropiedad t where t.djId = :djId and t.estado = '1'");
			
			q.setParameter("djId", djId);	
			
			lstRpTransferenciaPropiedad = q.getResultList();

			//rpTransferenciaPropiedad = (RpTransferenciaPropiedad) q.getSingleResult();
			if(lstRpTransferenciaPropiedad != null && lstRpTransferenciaPropiedad.size()>0){
				rpTransferenciaPropiedad = lstRpTransferenciaPropiedad.get(0);
			}
			
			
		} catch (NoResultException e) {			
		} catch (Exception e) {
			throw new SisatException("Error en la tabla rp_transferencia_propiedad. ".concat(e.getMessage()));
		}
		return rpTransferenciaPropiedad; 
	}
	
	public List<BuscarPersonaDTO> getTransferentePropiedadReImpresion(Integer djId,String TipoTransferencia)throws Exception {
		return getService().getTransferentePropiedadReImpresion(djId, TipoTransferencia);
	}
	
	public RpFiscaInspeccion getRpFiscaInspeccion(Integer djId,Integer predioId, Integer personaId)
			throws Exception{
		RpFiscaInspeccion inspeccion = new RpFiscaInspeccion();
		try{
			Query q = em
					.createQuery("from RpFiscaInspeccion i where i.djId = :djId and i.predioId = :predioId and i.personaId = :personaId and i.estado = '1'");
			
			q.setParameter("djId", djId);
			q.setParameter("predioId", predioId);
			q.setParameter("personaId", personaId);
			
			inspeccion = (RpFiscaInspeccion) q.getSingleResult();
			
		} catch (NoResultException e) {			
		} catch (Exception e) {
			throw new SisatException("Error en la tabla rp_fisca_inspeccion. ".concat(e.getMessage()));
		}
		
		return inspeccion;
	}
	
	public void registrarRpFiscaInspeccion(RpFiscaInspeccion inspeccionFisca)throws Exception{

		if (inspeccionFisca.getInspeccionId() == null) {
			Integer id = getService().obtenerCorrelativoTabla(
					"rp_fisca_inspeccion");

			inspeccionFisca.setInspeccionId(id);
			inspeccionFisca.setFechaRegistro(DateUtil.getCurrentDate());		
			inspeccionFisca.setTerminal(getUser().getTerminal());		
			inspeccionFisca.setUsuarioId(getUser().getUsuarioId());
			
			em.persist(inspeccionFisca);
		}else {			
			
			inspeccionFisca.setFechaActualizacion(DateUtil.getCurrentDate());		
			inspeccionFisca.setTerminal(getUser().getTerminal());		
			inspeccionFisca.setUsuarioId(getUser().getUsuarioId());
			
			em.merge(inspeccionFisca);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GnTipoDocumento> obtenerTipoDocumentos() throws SisatException {
		
		List<GnTipoDocumento> result  = null;
		try{
			Query q = em.createQuery("from GnTipoDocumento"); 
			
			result = q.getResultList();
				
		}catch (Exception e) {
			throw new SisatException(e.getMessage(),e);
		}

		return result;
	} 
	
	public ArrayList<UbicacionDTO> findGnUbicacionById(Integer ubicacionId) throws SisatException{
		return getService().findGnUbicacionById(ubicacionId);
	}
	
	public ArrayList<FindRpDjPredial> getRpDjpredial2(String apellidosNombres,String razonSocial,Integer tipoDocIdentidad,String numeroDocIdentidad,String codigoPredio,
			Integer tipoViaId,Integer viaId,Integer sectorId,Integer lugarId,String direccion,
			Integer djId,Integer personaId, String codAntSatcaj, String codigoAnterior,  Integer numeroCuadra, Integer lado, Integer numeroManzana, String numeroVia, Boolean esPropietario) throws Exception{
		return getService().getRpDjpredial2(apellidosNombres, razonSocial, tipoDocIdentidad, numeroDocIdentidad, codigoPredio, tipoViaId, viaId, sectorId, lugarId, direccion, djId,personaId, 
				codAntSatcaj, codigoAnterior, 
				numeroCuadra, lado, numeroManzana, numeroVia, esPropietario);
	}
	public ArrayList<FindRpDjPredial> getRpDjpredial3(String apellidosNombres,String razonSocial,Integer tipoDocIdentidad,String numeroDocIdentidad,String codigoPredio,
			Integer tipoViaId,Integer viaId,Integer sectorId,Integer lugarId,String direccion,
			Integer djId,Integer personaId, String codAntSatcaj, String codigoAnterior,  Integer numeroCuadra, Integer lado, Integer numeroManzana, String numeroVia, Boolean esPropietario) throws Exception{
		return getService().getRpDjpredial3(apellidosNombres, razonSocial, tipoDocIdentidad, numeroDocIdentidad, codigoPredio, tipoViaId, viaId, sectorId, lugarId, direccion, djId,personaId, 
				codAntSatcaj, codigoAnterior, 
				numeroCuadra, lado, numeroManzana, numeroVia, esPropietario);
	}
	
	public RpTipoObraPeriodo getRpTipoObraPeriodo(Integer tipoObraId, Integer periodo) throws SisatException{
		RpTipoObraPeriodo tipoObraPeriodo = new RpTipoObraPeriodo();
		try{
			Query q = em
					.createQuery("from RpTipoObraPeriodo t where t.tipoObraId = :tipoObraId and t.periodo = :periodo and t.estado = '1'");
			
			q.setParameter("tipoObraId", tipoObraId);
			q.setParameter("periodo", periodo);
			
			
			tipoObraPeriodo = (RpTipoObraPeriodo) q.getSingleResult();
			
		} catch (NoResultException e) {			
		} catch (Exception e) {
			throw new SisatException("Error en la tabla rp_tipo_obra_periodo. ".concat(e.getMessage()));
		}
		
		return tipoObraPeriodo;
	}
	
	
	@Override
	public Integer getNumeroManzanaByUbicacionId(Integer ubicacionId) throws SisatException{
		return getService().getNumeroManzanaByUbicacionId(ubicacionId);
	}
	
	@Override
	public ArrayList<UbicacionDTO> findGnViaV3(Integer numeroManzana, String descripcion) throws SisatException {

		return getService().findGnViaV3(numeroManzana, descripcion);
	}
	
	public int obtenerAnnioMax(Integer per, Integer idPredio) throws Exception{
		return getService().obtenerAnnioMax(per, idPredio);
	}

	/**
	 * Obtiene la cantidad de niveles y secciones con la misma denominacion 
	 */
	public Integer getExisteMismoNivel(Integer DjId,Integer construccionId,Integer nroNivel,String seccion)throws Exception{
		return getService().getExisteMismoNivel(DjId, construccionId, nroNivel, seccion);
	}
	
	public Integer getExisteMismoNivel(Integer DjId,Integer nroNivel,String seccion)throws Exception{
		return getService().getExisteMismoNivel(DjId,nroNivel,seccion);
	}
	
	public Integer getReqInspeccionByDj(Integer djId)throws Exception{
		return getService().getReqInspeccionByDj(djId);
	}
	
	public ArrayList<RpDjuso> getAllRpDjusoNuevo(int arbitrioId)
			throws Exception{
		return getService().getAllRpDjusoNuevo(arbitrioId);
	}
	
	public Integer getAllTramo(int anio)
			throws Exception{
		return getService().getAllTramo(anio);
	}
	
	public ArrayList<RpDjuso> getAllRpDjusoTramos(int arbitrioId)
			throws Exception{
		return getService().getAllRpDjusoTramos(arbitrioId);
	}
	
	public BigDecimal obtenerPorcentajeDepreciacion(int depreciacionId,int annoDeterminacion,int materialId,int conservacionId,int antiguedad)throws Exception{
		return getService().obtenerPorcentajeDepreciacion(depreciacionId, annoDeterminacion, materialId, conservacionId,antiguedad);
	}
	
	public List<RpMaterialPredominante> getMaterialPredominanteObra(Integer tipoObraId, Integer periodo) throws Exception{
		return getService().getMaterialPredominanteObra(tipoObraId, periodo);
	}
	
	public DtFactorOfic getFactorOficializacion(Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		List<Object> lista=getService().findByNamedQuery("getDtFactorOficByPeriodo", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtFactorOfic)lista.get(0);
		}
		return null; 
	}
	
	public List<FotoPredioDTO> getFotoPredio(Integer predioId) throws Exception{
		return getService().getFotoPredio(predioId);
	}
	
	public List<FotoPredioConstruccionesDTO> getFotoPredioConstrucciones(Integer predioId) throws Exception{
		return getService().getFotoPredioConstrucciones(predioId);
	}
	
	public List<FotoPredioInstalacionesDTO> getFotoPredioInstalaciones(Integer predioId) throws Exception{
		return getService().getFotoPredioInstalaciones(predioId);
	}
	
	public int registrarFotoInspeccion(int opcion, int personaId,int predioId, int fotoInspecionId, int inspectorId, int flagValida, String glosa, int usuarioId, String terminal)throws Exception{
		return getService().registrarFotoInspeccion(opcion,personaId,predioId,fotoInspecionId,inspectorId,flagValida,glosa,usuarioId,terminal);
	}
	
	public List<FotoPredioInspeccionDTO> getFotoInspeccion(int opcion, int personaId,int predioId, int fotoInspecionId, int inspectorId, int flagValida, String glosa, int usuarioId, String terminal)throws Exception{
		return getService().getFotoInspeccion(opcion,personaId,predioId,fotoInspecionId,inspectorId,flagValida,glosa,usuarioId,terminal);
	}
}