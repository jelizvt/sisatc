/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sat.sisat.predial.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.DtArancelUbicacion;
import com.sat.sisat.persistence.entity.DtCercaniaParque;
import com.sat.sisat.persistence.entity.DtFrecuenciaLimpieza;
import com.sat.sisat.persistence.entity.DtFrecuenciaRecojo;
import com.sat.sisat.persistence.entity.DtGrupoCercania;
import com.sat.sisat.persistence.entity.DtZonaSeguridad;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUbicacion;
import com.sat.sisat.persistence.entity.GnDenominacionUrbana;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoAgrupamiento;
import com.sat.sisat.persistence.entity.GnTipoDenoUrbana;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnTipoEdificacion;
import com.sat.sisat.persistence.entity.GnTipoIngreso;
import com.sat.sisat.persistence.entity.GnTipoInterior;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.persistence.entity.MpCondiEspePredio;
import com.sat.sisat.persistence.entity.MpDireccion;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpPersonaDomicilio;
import com.sat.sisat.persistence.entity.MpPredio;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.MpSituacionEmpresarial;
import com.sat.sisat.persistence.entity.MpSubtipoPersona;
import com.sat.sisat.persistence.entity.MpTipoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persistence.entity.MpTipoRelacion;
import com.sat.sisat.persistence.entity.RjComponenteConstruccion;
import com.sat.sisat.persistence.entity.RjDocuAnexo;
import com.sat.sisat.persistence.entity.RjDocumentoSustentatorio;
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
import com.sat.sisat.persistence.entity.RpTipoAdquisicion;
import com.sat.sisat.persistence.entity.RpTipoNivel;
import com.sat.sisat.persistence.entity.RpTipoObra;
import com.sat.sisat.persistence.entity.RpTipoObraPeriodo;
import com.sat.sisat.persistence.entity.RpTipoTierraRustico;
import com.sat.sisat.persistence.entity.RpTipoUso;
import com.sat.sisat.persistence.entity.RpTransferenciaPropiedad;
import com.sat.sisat.persistence.entity.RpUbicacionPredio;
import com.sat.sisat.persistence.entity.TgTabla;
import com.sat.sisat.predial.dto.FindDireccion;
import com.sat.sisat.predial.dto.FindGnDenUrbana;
import com.sat.sisat.predial.dto.FindMpDireccion;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.FindMpRelacionado;
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
@Local
public interface RegistroPrediosBoLocal {

	public ArrayList<FindRpDjPredial> getRpDjpredial(String codigo_predio,
			int dj_id, int viaId, int denoUrbanaId, Integer personaId)
			throws Exception;

	public List<MpTipoDocuIdentidad> getAllMpTipoDocumento() throws Exception;

	public List<MpTipoPersona> getAllMpTipoPersona() throws Exception;

	public ArrayList<FindMpPersona> getmpPersona(Integer personaId,
			String apePaterno, String apeMaterno, String primerNombre,
			String segundoNombre, String documentoidentidad) throws Exception;

	public int getNextMpPersonaId() throws Exception;

	public int guardarPersona(Integer mppersona_id) throws Exception;

	public ArrayList<MpTipoPersona> findMpTipoPersona(Integer tipoPersonaId)
			throws Exception;

	public ArrayList<MpTipoDocuIdentidad> findMpTipoDocuIdentidad(
			Integer tipoPersonaId) throws Exception;

	public int getNextMpDireccion() throws Exception;

	public String ConcatenarMpDireccion(MpDireccion direccion,
			HashMap<Integer, String> mapIGnTipoEdificacion,
			HashMap<Integer, String> mapIGnTipoInterior,
			HashMap<Integer, String> mapIGnTipoIngreso,
			HashMap<Integer, String> mapIGnTipoVia,
			HashMap<Integer, String> mapIGnTipoDenUrbana);

	public ArrayList<RelacionadosDTO> getAllRelacionadoDTO(Integer personaId)
			throws Exception;

	public int guardarMpdireccion(MpDireccion direccion) throws Exception;

	public int guardarMpPersonadireccion(MpPersonaDomicilio mpPersonaDomicilio)
			throws Exception;

	public List<MpTipoCondicionEspecial> getAllMpTipoCondicionEspecial()
			throws Exception;

	public List<MpSituacionEmpresarial> getAllMpSituacionEmpresarial()
			throws Exception;

	public List<MpSubtipoPersona> finMpSubtipoPersona(Integer tipoPersonaId)
			throws Exception;

	public ArrayList<MpTipoDocuIdentidad> findMpTipoDocuIdentidad(
			Integer tipoPersonaId, Integer subtipoPersonaId) throws Exception;

	public ArrayList<FindMpRelacionado> findMpRelacionadoPersona(
			Integer Persona_id) throws Exception;

	public ArrayList<FindMpDireccion> finMpDireccionPersona(int persona_id)
			throws Exception;

	public List<MpTipoCondicionEspecial> findMpTipoCondicionEspecial(
			Integer tipo_cond_especial_id) throws Exception;

	public List<MpSituacionEmpresarial> findMpSituacionEmpresarial(
			Integer situacion_empresarial_id) throws Exception;

	public List<MpTipoRelacion> getAllMpTipoRelacion() throws Exception;

	public List<MpTipoRelacion> findMpTipoRelacion(Integer tipoRelacionId)
			throws Exception;

	public int getNextMpRelacionadoId() throws Exception;

	public int guardarMpRelacionado(MpRelacionado mpRelacionado)
			throws Exception;

	/**
	 * Papeletas :inicio
	 */

	public int generarNuevoLote() throws Exception;

	/**
	 * externo:fin
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<GnTipoVia> getAllGnTipoVia() throws Exception;

	// rp_ubicacion_predio
	public List<RpUbicacionPredio> getAllRpUbicacionPredio() throws Exception;

	public List<RpTipoAdquisicion> getAllRpTipoAdquisicion() throws Exception;

	// Registro de datos de la construccion
	// Tipo nivel
	public List<RpTipoNivel> getAllRpTipoNivel() throws Exception;

	// Mes enero - diciembre

	// --UBICACION DE PREDIO
	// GnTipoAgrupamiento
	public List<GnTipoAgrupamiento> getAllGnTipoAgrupamiento() throws Exception;

	// GnTipoEdificacion
	public List<GnTipoEdificacion> getAllGnTipoEdificacion() throws Exception;

	// GnTipoIngreso
	public List<GnTipoIngreso> getAllGnTipoIngreso() throws Exception;

	// GnTipoInterior
	public List<GnTipoInterior> getAllGnTipoInterior() throws Exception;

	// GnSector
	public List<GnSector> getAllGnSector() throws Exception;

	// Buscar vias
	public ArrayList<UbicacionDTO> findGnVia(Integer tipo_via_id,
			Integer sector_id, String descripcion) throws Exception;

	// Buscar vias
	public ArrayList<FindGnDenUrbana> findGnDenUrbana(Integer tipoDenUrbanaId,
			Integer sectorId, String descripcion) throws Exception;

	public int guardarRpDjdireccion(RpDjdireccion direccion) throws Exception;

	// --
	public List<RpCondicionPropiedad> getAllRpCondicionPropiedad()
			throws Exception;

	public List<MpCondiEspePredio> getAllMpCondiEspePredio() throws Exception;

	public int getNextPredioId() throws Exception;

	public int getNextDjId() throws Exception;

	public MpPredio getMpPredio(Integer predioId) throws Exception;

	public int guardarMpPredio(MpPredio mpPredio) throws Exception;

	public int actualizaMpPredio(MpPredio mpPredio) throws Exception;

	public int guardarRvDocumentoSustentatorio(
			RjDocumentoSustentatorio rvDocumentoSustentatorio) throws Exception;

	public ArrayList<RjDocumentoSustentatorio> getRvDocumentoSustentatorio(
			Integer dj_id, Integer tipo_docu_sustento_id) throws Exception;

	// PANTALLA DATOS DE LA CONSTRUCCION
	// Tipo Nivel
	// public List<RpTipoNivel> getAllRpTipoNivel()
	// Mes construccion
	public List<RjMes> getAllRjMes() throws Exception;

	// Area comun contr. :
	// RjComponenteConstruccion
	public List<RjComponenteConstruccion> getAllRjComponenteConstruccion(
			int anioVigencia, int tipoComponente) throws Exception;

	// Area comun contr. :
	// rj_unidad_medida_acc
	public List<TgTabla> getAllTgTabla(String descripcion) throws Exception;

	// Material Predominante : falta datos
	// rp_material_predominante
	// RpMaterialPredominante
	public List<RpMaterialPredominante> getAllRpMaterialPredominante()
			throws Exception;

	// Estado de concervacion : falta datos
	// rj_estado_concervacion
	// RjEstadoConservacion
	public List<RjEstadoConservacion> getAllRjEstadoConservacion()
			throws Exception;

	// Clasificacion depreciacion : falta datos
	// rj_tipo_depreciacion
	// RjTipoDepreciacion
	public List<RjTipoDepreciacion> getAllRjTipoDepreciacion() throws Exception;

	public ArrayList<RpDjconstruccion> getAllRpDjconstruccion(Integer dj_id,
			Integer periodo) throws Exception;

	public ArrayList<RpDjconstruccion> getAllRpDjconstruccion2(Integer dj_id,
			Integer periodo) throws Exception;

	public int actualizaEstadoRpDjpredial(RpDjpredial rpDjpredial, String estado)
			throws Exception;

	public String getFlagDjAnno(Integer dj, Integer periodo, Integer predioid)
			throws Exception;

	public int guardarRpDjpredial(RpDjpredial rpDjpredial) throws Exception;

	public int actualizaRpDjpredial(RpDjpredial rpDjpredial) throws Exception;

	public RpDjconstruccion getRpDjconstruccion(Integer djId,
			Integer construccionId) throws Exception;

	public List<GnTipoDenoUrbana> getAllGnTipoDenoUrbana() throws Exception;

	public RpDjpredial getRpDjpredial(Integer djId) throws Exception;

	// RpDjpredial

	public ArrayList<GnVia> findGnVia(Integer tipo_via_id) throws Exception;

	public ArrayList<GnDenominacionUrbana> findGnDenUrbana(
			Integer tipoDenUrbanaId) throws Exception;

	public List<RpTipoTierraRustico> getAllRpTipoTierraRustico()
			throws Exception;

	public List<RpAltitud> getAllRpAltitud() throws Exception;

	public List<RpCategoriaRustico> getAllRpCategoriaRustico() throws Exception;

	public int existDjDireccion(Integer dj_id) throws Exception;

	public int getDjDireccionActive(Integer dj_id) throws Exception;

	public int desactiveDirecciones(Integer dj_id, Integer direccion_id)
			throws Exception;

	public int existDjConstruccion(Integer dj_id) throws Exception;

	public int getUltimoConstruccionId(Integer dj_id) throws Exception;

	public RpDjdireccion getRpDjDireccion(Integer DjId) throws Exception;

	public GnVia getGnVia(Integer ViaId) throws Exception;

	public GnDenominacionUrbana getGnDenominacionUrbana(Integer DenoId)
			throws Exception;

	public String ConcatenarDomicilioUrbano(RpDjdireccion direccion,
			HashMap<Integer, String> mapIGnTipoEdificacion,
			HashMap<Integer, String> mapIGnTipoInterior,
			HashMap<Integer, String> mapIGnTipoIngreso,
			HashMap<Integer, String> mapIGnTipoAgrupamiento,
			HashMap<Integer, String> mapIGnTipoVia,
			HashMap<Integer, String> mapIGnTipoDenUrbana);

	public ArrayList<ListRpDjPredial> getListRpDjpredial(Integer predioId,
			Integer personaId) throws Exception;

	public ArrayList<ListRpDjPredial> getListRpDjpredialTodos(Integer predioId,
			Integer personaId) throws Exception;

	public FindPersona getFindPersona(Integer personaId) throws Exception;

	public ArrayList<FindPersona> getFindPersona() throws Exception;

	public ArrayList<FindPersona> getFindEmpresas() throws Exception;

	public Integer getPersonaId(Integer tipDoc, String numeroDoc)
			throws Exception;

	public ArrayList<FindRpDjPredial> getBRpDjpredial(Integer personaId,
			String codigopredio, String periodo, String TipoPredio)
			throws Exception;

	public int guardarRpDjconstruccion(RpDjconstruccion rpDjconstruccion)
			throws Exception;

	public int actualizaRpDjconstruccion(RpDjconstruccion rpDjconstruccion)
			throws Exception;

	public FindDireccion verificaDireccion(Integer djId, Integer idVia,
			String numero1, String letra1, String numero2, String letra2,
			Integer edificacionId, String edificacion, Integer interiorId,
			String interior, String referencia) throws Exception;

	// --
	public ArrayList<RpTipoObra> getAllRpTipoObra(Integer categoriaObraId,
			Integer codMinVivienda) throws Exception;

	public RpInstalacionDj getRpInstalacionDj(int djId, int instalacionId)
			throws Exception;

	public RpOtrosFrente getRpOtrosFrente(int djId, int otroFrenteId)
			throws Exception;

	public RpDjuso getRpDjuso(int arbitrioId, int usoId) throws Exception;

	public RjDocuAnexo getRjDocuAnexo(int DjId, int docuAnexoId)
			throws Exception;

	public int actualizarRpInstalacionDj(RpInstalacionDj rpInstalacionDj)
			throws Exception;

	public int guardarRpInstalacionDj(RpInstalacionDj rpInstalacionDj)
			throws Exception;

	public ArrayList<RpInstalacionDj> getAllRpInstalacionDj(int djId)
			throws Exception;

	public int guardarRpOtrosFrente(RpOtrosFrente rpOtrosFrente)
			throws Exception;

	public ArrayList<RpOtrosFrente> getAllRpOtrosFrente(int djId)
			throws Exception;

	public List<RpTipoUso> getAllRpTipoUso() throws Exception;

	public List<RpTipoUso> getAllRpTipoUsoSinTerreno() throws Exception;

	public int actualizaAreaUsoRpDjuso(RpDjuso uso) throws Exception;

	public ArrayList<RpDjuso> getAllRpDjuso(int arbitrioId) throws Exception;

	public ArrayList<RjDocuAnexo> getAllRjDocuAnexo(int djId) throws Exception;

	public int guardarRjDocuAnexo(RjDocuAnexo rjDocuAnexo) throws Exception;

	public int deleteRjDocuAnexo(int djId, int docuAnexoId) throws Exception;

	public int deleteRpDjconstruccion(int djId, int construccionId)
			throws Exception;

	public int deleteRpDjuso(int djarbitrioId, int djusoId) throws Exception;

	public int deleteRpOtrosFrente(int djId, int otroFrenteId) throws Exception;

	public int deleteRpInstalacionDj(int djId, int instalacionId)
			throws Exception;

	public Integer getDjArbitrioId(int djId) throws Exception;

	public int guardarDjArbitrioId(RpDjarbitrio rpDjarbitrio) throws Exception;

	// --

	public int guardarRpDjuso(RpDjuso rpDjuso) throws Exception;

	public int getUltimoDjUsoId(Integer djarbitrio_id) throws Exception;

	public int actualizaRpDjuso(RpDjuso rpDjuso) throws Exception;

	public int deleteRpDjUsoDetalle(int rpDjusoId) throws Exception;

	public int guardarRpDjusoDetalle(RpDjusoDetalle detalle) throws Exception;

	public RpDjusoDetalle getRpDjusoDetalle(int dJUsoId, int construccionId)
			throws Exception;

	public ArrayList<RpDjusoDetalle> getAllRpDjusoDetalle(int dJUsoId)
			throws Exception;

	public BigDecimal getAreaUsada(int djarbitrioId, int construccionId)
			throws Exception;

	public BigDecimal getAreaUsadaRecalculo(int djusoId, int djarbitrioId,
			int construccionId) throws Exception;

	public Integer registrarAdquirientes(
			List<BuscarPersonaDTO> lstTransferentes, Integer NewDjId,
			MpPersona usuario) throws Exception;

	public Integer registrarDocAnexos(List<AnexosDeclaVehicDTO> lstAnexos,
			Integer NewDjId, MpPersona usuario) throws Exception;

	public boolean cambiarEstadoDjv(int djId, String estado) throws Exception;

	public List<BuscarPersonaDTO> getTransferentePropiedad(Integer djId,
			String TipoTransferencia) throws Exception;

	public List<AnexosDeclaVehicDTO> getDocumentosAnexos(Integer djId)
			throws Exception;

	// caltamirano:ini
	public abstract boolean updateDjPredial(int djId, Integer djAnteriorId,
			String fiscalizado, String fiscaAceptada, String fiscaCerrada);

	public RpDjpredial duplicarPredioTemp(Integer DjIdAnt, int userId,
			String terminal);

	public RpDjpredial copiarDjpOtroAnio(Integer DjIdAnt, int anioCrear,
			int userId, String terminal) throws SisatException;

	public boolean isDjpAtYear(int year, int personaId, int predioId);

	// caltamirano:fin

	/**
	 * Valida que para hacer una copia de una dj predial a otro año, este afecto
	 * al contribuyente.
	 * 
	 * @param djpId
	 *            Identificador de la declaración jurada predial.
	 * @param anioCrear
	 *            Año al que se desea hacer la copía.
	 * @return Verdadero o falso dependiendo si el contribuyente esta afecto o
	 *         no.
	 */
	public boolean estaEnRangoAfecContrib(int djpId, int anioCrear);

	public List<GnVia> getAllGnVia(Integer tipoViaId) throws SisatException;

	public List<GnTipoVia> getAllGnTipoVia(Integer gnViaId)
			throws SisatException;

	public ArrayList<UbicacionDTO> findGnViaV2(Integer tipo_via_id,
			Integer viaId, String descripcion) throws SisatException;

	public void eliminarDJPredio(ListRpDjPredial djpredial, int personaId)
			throws SisatException;

	public void inactivarDJPredio(ListRpDjPredial djpredial, int personaId)
			throws SisatException;

	public ArrayList<UbicacionDTO> findGnUbicacion(Integer tipoViaId,
			Integer viaId, Integer sectorId, Integer lugarId)
			throws SisatException;

	public void agregarUbicacion(UbicacionDTO nuevaUbicacion)
			throws SisatException;

	public void agregarArancelUbicacion(DtArancelUbicacion arancelUbicacion)
			throws SisatException;

	public void agregarFrecuenciaRecojo(DtFrecuenciaRecojo frecuenciaRecojo)
			throws SisatException;

	public void agregarFrecuenciaLimpieza(
			DtFrecuenciaLimpieza frecuenciaLimpieza) throws SisatException;

	public void agregarCercaniaParque(DtCercaniaParque cercaniaParque)
			throws SisatException;

	public void agregarZonaSeguridadUbicacion(
			DtZonaSeguridadUbicacion zonaSeguridadUbicacion)
			throws SisatException;

	public List<DtGrupoCercania> getAllDtGrupoCercania(Integer periodo)
			throws SisatException;

	public List<DtZonaSeguridad> getAllDtZonaSeguridad(Integer periodo)
			throws SisatException;

	public DtArancelUbicacion getDtArancelUbicacion(Integer ubicacionId,
			Integer periodo) throws SisatException;

	public DtFrecuenciaRecojo getDtFrecuenciaRecojo(Integer ubicacionId,
			Integer periodo) throws SisatException;

	public DtFrecuenciaLimpieza getDtFrecuenciaLimpieza(Integer ubicacionId,
			Integer periodo) throws SisatException;

	public DtCercaniaParque getDtCercaniaParque(Integer ubicacionId,
			Integer periodo) throws SisatException;

	public DtZonaSeguridadUbicacion getDtZonaSeguridadUbicacion(
			Integer ubicacionId, Integer periodo) throws SisatException;

	public RpTransferenciaPropiedad getRpTransferenciaPropiedad(int djId)
			throws SisatException;

	public List<BuscarPersonaDTO> getTransferentePropiedadReImpresion(
			Integer djId, String TipoTransferencia) throws Exception;

	public RpFiscaInspeccion getRpFiscaInspeccion(Integer djId,
			Integer predioId, Integer personaId) throws Exception;

	public void registrarRpFiscaInspeccion(RpFiscaInspeccion inspeccionFisca)
			throws Exception;

	public List<GnTipoDocumento> obtenerTipoDocumentos() throws SisatException;

	public ArrayList<UbicacionDTO> findGnUbicacionById(Integer ubicacionId)
			throws SisatException;

	public ArrayList<FindRpDjPredial> getRpDjpredial2(String apellidosNombres,
			String razonSocial, Integer tipoDocIdentidad,
			String numeroDocIdentidad, String codigoPredio, Integer tipoViaId,
			Integer viaId, Integer sectorId, Integer lugarId, String direccion,
			Integer djId, Integer personaId, String codAntSatcaj,
			String codigoAnterior, Integer numeroCuadra, Integer lado,
			Integer numeroManzana, String numeroVia, Boolean esPropietario)
			throws Exception;

	public ArrayList<FindRpDjPredial> getRpDjpredial3(String apellidosNombres,
			String razonSocial, Integer tipoDocIdentidad,
			String numeroDocIdentidad, String codigoPredio, Integer tipoViaId,
			Integer viaId, Integer sectorId, Integer lugarId, String direccion,
			Integer djId, Integer personaId, String codAntSatcaj,
			String codigoAnterior, Integer numeroCuadra, Integer lado,
			Integer numeroManzana, String numeroVia, Boolean esPropietario)
			throws Exception;

	public RpTipoObraPeriodo getRpTipoObraPeriodo(Integer tipoObraId,
			Integer periodo) throws SisatException;

	public Integer getNumeroManzanaByUbicacionId(Integer ubicacionId)
			throws SisatException;

	public ArrayList<UbicacionDTO> findGnViaV3(Integer numeroManzana,
			String descripcion) throws SisatException;

	public Integer getReqInspeccionByDj(Integer djId) throws Exception;

	public RpCategoriaObra getRpCategoriaObraById(Integer categoriaObraId)
			throws Exception;
	
	public List<FindRpTipoObraDTO> getAllRpCaregoriaObraXIdMV(Integer idAnteriorTO)throws Exception;
	
	public ArrayList<RpCategoriaObra> getAllRpCategoriaObra(Integer periodo)
			throws Exception;

	public ArrayList<RpDjuso> getAllRpDjusoNuevo(int arbitrioId) throws Exception;
	
	public ArrayList<RpDjuso> getAllRpDjusoTramos(int arbitrioId) throws Exception;
	
	public BigDecimal obtenerPorcentajeDepreciacion(int depreciacionId,int annoDeterminacion,int materialId,int conservacionId,int antiguedad)throws Exception;
	
	public List<RpMaterialPredominante> getMaterialPredominanteObra(Integer tipoObraId, Integer periodo)throws Exception;
	
	public List<FotoPredioDTO> getFotoPredio(Integer predioId)throws Exception;
	
	public List<FotoPredioConstruccionesDTO> getFotoPredioConstrucciones(Integer predioId)throws Exception;
	
	public List<FotoPredioInstalacionesDTO> getFotoPredioInstalaciones(Integer predioId)throws Exception;
	
	public int registrarFotoInspeccion(int opcion, int personaId,int predioId, int fotoInspecionId, int inspectorId, int flagValida, String glosa, int usuarioId, String terminal)throws Exception;

	public List<FotoPredioInspeccionDTO> getFotoInspeccion(int opcion, int personaId,int predioId, int fotoInspecionId, int inspectorId, int flagValida, String glosa, int usuarioId, String terminal)throws Exception;
}
