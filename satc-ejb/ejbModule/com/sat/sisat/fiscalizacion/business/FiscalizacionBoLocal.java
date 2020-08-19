package com.sat.sisat.fiscalizacion.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.faces.model.SelectItem;

import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteExigible;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecTipo;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.MpFiscalizador;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorArea;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorDto;
import com.sat.sisat.determinacion.vehicular.dto.DatosNecesariosDeterDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.dto.DatosNecesariosDeclaracionDTO;
import com.sat.sisat.fiscalizacion.dto.FindInpscDocTipo;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByDetalle;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByHorario;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionById;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByIdAsociada;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByResultado;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionDj;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionDocCargoTipo;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorialDetalle;
import com.sat.sisat.persistence.entity.DtTasaVehicular;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.RpFiscalizacionHorario;
import com.sat.sisat.persistence.entity.RpFiscalizacionHorarioDetalle;
import com.sat.sisat.persistence.entity.RpFiscalizacionInspeccion;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.persistence.entity.RpFiscalizacionProgramaDetalle;
import com.sat.sisat.persistence.entity.RvCategoriaVehiculo;
import com.sat.sisat.persistence.entity.RvClaseVehiculo;
import com.sat.sisat.persistence.entity.RvDjvehicular;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculo;
import com.sat.sisat.persistence.entity.RvOmisosDetalleVehicular;
import com.sat.sisat.persistence.entity.RvOmisosVehicular;
import com.sat.sisat.persistence.entity.RvSustentoVehicular;
import com.sat.sisat.persistence.entity.RvTipoCarroceria;
import com.sat.sisat.persistence.entity.RvTipoMotor;
import com.sat.sisat.persistence.entity.RvVehiculo;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.dto.MarcaModeloTemporalDTO;

@Local
public interface FiscalizacionBoLocal {

	public abstract List<FindInpscDocTipo> getAllTipoDoc()	throws Exception;
	
	public abstract List<RpFiscalizacionPrograma> getAllTipoPrograma()	throws Exception;
	
	public abstract List<MpFiscalizador> getAllInspector()	throws Exception;
	
	public abstract List<RpFiscalizacionHorario> getAllHorario() throws Exception;
	
	public abstract int guardarRequerimiento (Integer tipoDocumento,String nroCorrelativo, Integer tipoPrograma,Integer nombreInspector, 
			Date fechaInspeccion,Integer codPersona,String observacion, String dirPersona,Date fechaNotifica,String anioInspeccion,
			Integer tipoDocumentoRef,String nroDocumentoRef,Integer omiso,Integer estado)
			throws Exception;
	
	public abstract int guardarRequerimientoDetalle (Integer inspectorId, String anio,Integer djId,Integer predioId,Date fechaInspeccion
			,Integer ubicacion,Integer sector,Integer tipoVia, Integer via,String manzana,String cuadra,String lado,String predioDir) throws Exception;
	
	public int guardarDetalleFip(Integer inspectorId, String anio,Integer djId,Integer predioId,
			Integer ubicacionId,Integer tipoViaId,Integer viaId,Integer sectorId,String manzana,String cuadra,String lado,
			Date fechaInspeccion,Integer inspcc_id,String direccion,String sector,String via,String tipoVia,String lugar,
			Integer inspectorIdAr,Date fechaInspeccionAr,Integer usuarioId,String terminal)
	throws Exception;
	
	
	public List<FindInspeccionHistorial> getAllInspecciones() throws Exception;
	
	public List<BuscarPersonaDTO> findPersona(Integer persId,String apeNom) throws Exception;
	public List<BuscarPersonaDTO> findPersona(int tipoDocuIdentidadId,String nroDocuIdentidad) throws Exception;
	
	public abstract List<FindInspeccionDj> getDeclaracionesInsp(Integer persona_id) throws Exception;
	
	public abstract List<FindInspeccionDj> getDeclaracionesInspById(Integer inspId,Integer personaId,Integer djId) throws Exception;
	
	public abstract List<FindInspeccionDj> getDeclaracionesInspByPersona(Integer personaId,Integer predioId,Integer anioId) throws Exception;
	
	public abstract List<FindInspeccionDj> getDeclaracionesInspeccionById(Integer inspId) throws Exception;
	
	public abstract List<FindInspeccionDj> getPrediosInspById(Integer inspId) throws Exception;
	
	public abstract List<FindRpDjPredial> getPrediosInspeccionById(Integer inspId) throws Exception;
//	public List<RpFiscalizacionInspeccion> getUltimaInspeccion(Integer persona) throws Exception ;
	
	public abstract int getUltimaInspeccion()throws Exception;
	
	public abstract void create(RpFiscalizacionHorarioDetalle rpFiscaHora) throws Exception;
	
	public abstract List<FindInspeccionById> getInspeccionById(Integer inspId) throws Exception;
	
	public RpFiscalizacionInspeccion getInspeccion(Integer inspId) throws Exception;
	
	public FindInspeccionById getInspecciones(Integer inspId) throws Exception;
	
	public abstract List<FindInspeccionByHorario> getInspeccionesHorario(Integer inspId) throws Exception;
	
	public abstract List<FindInspeccionByHorario> getInspeccionesHorarioAr(Integer inspId) throws Exception;
	
	public abstract List<FindInspeccionByDetalle> getInspeccionesDetalle(Integer inspId) throws Exception;
	
	public abstract List<FindInspeccionDocCargoTipo> getAllTipoDocCargo() throws Exception;
	
	public abstract List<FindInspeccionDocCargoTipo> getCorrelativoCargo(Integer tipoDocId) throws Exception;
	
	public String correlativo(Integer tipoDocId) throws Exception;
	
	public abstract int actualizarRequerimiento 
			   (Integer tipoDocumento,String nroDocumentoResultado, Date fechaNotResultado,
		       Integer tipoEsquela,String nroDocumentoEsquela, Date fechaNotEsquela,
		       Integer tipoAr,String nroDocumentoAr, 
		       Integer inspId,Integer estado,Date fechaNotAr)
	throws Exception;
	
	public abstract int actualizarRequerimientoNotificacion 
	   			(Integer nombreInspector, Date fechaInspeccion,
	   					Integer codPersona,String observacion, String dirPersona,Date fechaNotifica,Integer estado,
	   					Integer inspId,Integer usuarioId,String terminal)
		   	   throws Exception;
	
	public int actualizarRequerimientoDetalle(Date fechaAr,Integer inspectorArId,Date fechaActualiza,Integer inspId) throws Exception;
	
	public int actualizarRequerimientoArFIP(Integer fipId,String fipNro,Date fechagenera,Date fechanotifica,Integer usuarioId,String terminal,Integer inspId) throws Exception;
	
	public List<FindInspeccionByResultado> getAllInspeccionesResultado(Integer inspId) throws Exception;
	
	public abstract List<FindInspeccionByHorario> getInspeccionesHorarioByResultado(Integer inspId) throws Exception;
	
	public List<FindInspeccionHistorial> findInspeccion(Integer contribuyenteId,String correlativo,String direccion,String apellidos) throws Exception;
	
	public abstract List<FindInspeccionHistorial> getInspeccionByCorrelativo(String correlativo, Integer tipo) throws Exception;
	
	public abstract List<FindInspeccionByHorario> getHorarioByInspector(Integer inspector,Date fechaInspeccion,Integer horaInspeccion) throws Exception;
	
	public List<FindInspeccionHistorialDetalle> getAllInspeccionesDetalle(Integer inspId) throws Exception;
	
	public abstract List<FindInspeccionDj> getInspeccionesByPredio(Integer personaId,Integer inspId) throws Exception;
	
	public abstract List<RpFiscalizacionProgramaDetalle> getAllAnios(Integer programaId) throws Exception;
	public abstract List<RpFiscalizacionProgramaDetalle> getAllAniosReq() throws Exception;
	public abstract List<RpFiscalizacionProgramaDetalle> getAllAniosById(Integer inspId) throws Exception;
	
	public List<FindInspeccionHistorial> getAllInspeccionesByPrograma(Integer programaId) throws Exception;
	
	public abstract void crearPrograma(RpFiscalizacionPrograma rpPrograma) throws Exception;
	
	public abstract int getUltimoPrograma()throws Exception;
	
	public abstract void crearProgramaDetalle(RpFiscalizacionProgramaDetalle rpProgramaDet) throws Exception;
	
	public List<RpFiscalizacionPrograma> getAllPrograma() throws Exception;
	
	public List<RpFiscalizacionPrograma> findPrograma(String programa) throws Exception;
	
	public abstract List<RpFiscalizacionProgramaDetalle> getAllProgramaAniosById(Integer programaId) throws Exception;
	
	public abstract int actualizarPrograma (String descripcion,Integer programaId)throws Exception;
	
	public List<MpFiscalizadorDto> getAllInspectores() throws Exception;
	
	public abstract List<MpFiscalizadorArea> getAllTipoArea() throws Exception;
	
	public abstract void crearInspector(MpFiscalizadorDto f) throws Exception;
	
	public MpFiscalizador buscarInspector(String identificador) throws Exception;

	public abstract List<MpFiscalizadorDto> getAllInspectoresById(Integer inspeId) throws Exception;
//	public MpFiscalizadorDto getAllInspectoresById(Integer inspeId) throws Exception;
	
	public int actualizarInspector(String codigo, String nombre,String dni, Integer telf,String direccion,Date inicio,Date fin,
			Integer unidad, Integer id) throws Exception;
	
	public abstract List<FindInspeccionByIdAsociada> getInspeccionByIdAsociada(Integer inspId) throws Exception;
	
	/*
	 * IMPUESTO VEHICULAR
	 */
	public List<FindCcLote> getAllLotes() throws Exception;
	public List<RvOmisosVehicular> getAllDetalleLotes(int loteId) throws Exception;
	public abstract List<RpFiscalizacionPrograma> getAllTipoProgramaVehicular()	throws Exception;
	public abstract int guardarRequerimientoVehicular (int loteId,int programaId) throws Exception;
	
	public List<FindInspeccionHistorial> getAllInspeccionesVehicular() throws Exception;
	
	/**
	 * Generacion del Impuesto Vehicular::
	 */
	public List<RvOmisosVehicular> getAllDetalleLotes2(int loteId,int estado) throws Exception;
	public DatosNecesariosDeterDTO getDatosNecesariosDeterminar(int omisoId,int loteId) throws Exception;
	public DtTasaVehicular getTasaVehicular(Integer anioAfec);
	public BigDecimal getUitAnio(int anio);
	public BigDecimal getValorMEF(int categoriaId,int marcaVehiculoId, int modeloVehiculoId, int periodoAfectacion,int periodoFabricacion) ;
	public BigDecimal getMontoAnioMenorAntig(int categoriaId, int  marcaVehiculoId, int modeloVehiculoId,int periodoAfectacion) ;
	public int guardarImpuestoVehicular (RvOmisosDetalleVehicular deter) throws Exception;
	public int actualizaEstadoRequerimiento (Integer requerimientoId, Integer omisoId,Integer estado) throws Exception;
	
	/**
	 * Generacion de la Declaración Jurada::
	 */
	public DatosNecesariosDeclaracionDTO getDatosNecesariosDeclaracion(int omisoId,int loteId) throws Exception;
	public Integer guardarVehiculo(RvVehiculo vehiculo) throws SisatException;
	public abstract RvDjvehicular guardarDJVehicular(RvDjvehicular dj) throws SisatException;
	public abstract void guardarDocAnexosDjVehicular(RvSustentoVehicular sv);
	public abstract boolean copiarDjvAOtroAnio(int djvId, int anioCrear,int contribId, int usuarioId, String terminal)throws SisatException;
	
	/**
	 * Generación de RD Vehicular::
	 */
	public List<FindCcLote> getAllLoteOmiso() throws Exception;
	public int actualizarDjOmisaVehicular (Integer loteId) throws Exception;
	public List<RvDjvehicular> getAllDjOmiso(Integer loteId) throws Exception;
	
	/***
	 * NUEVO
	 */
	public abstract List<RvClaseVehiculo> getAllClaseVehiculos()	throws Exception;
	public abstract List<RvCategoriaVehiculo> getAllCategoriaVehiculos()	throws Exception;
	public abstract List<RvMarca> getAllMarcaVehiculos()	throws Exception;
	public abstract List<RvTipoMotor> getAllMotorVehiculos()	throws Exception;
	public abstract List<RvTipoCarroceria> getAllCarroceriaVehiculos()	throws Exception;
	
	public List<RvOmisosDetalleVehicular> getAllDetalleVerificacion(int reqId) throws Exception ;
	
	public abstract List<RvMarca> findRvMarca(int categoria) throws Exception;
	public abstract List<RvModeloVehiculo> getAllRvModeloVehiculo(int marcaVehiculoId, int categoriaVehiculoId) throws Exception;
	public DatosNecesariosDeterDTO getAllAnioFabricacion(int reqId,int anio) throws Exception;
	public BigDecimal getMontoAnioMenorAntigDetalle(int categoriaId, int  marcaVehiculoId, int modeloVehiculoId,int periodoAfectacion)throws SisatException ;
	public int actualizarImpuestoVehicular (RvOmisosDetalleVehicular deter) throws Exception;
	public DatosNecesariosDeclaracionDTO getDatosNecesariosDeclaracionDetalle(int reqDetId,int reqId,int anio) throws Exception;
	public Integer getVehiculoFiscalizado(int reqId ) throws Exception;
	public List<FindCcLote> getAllLotesVehicular() throws Exception;
	public List<Integer> estadisticaLoteMasivo(int loteId) throws Exception;
	public abstract List<MarcaModeloTemporalDTO> getMarcaModeloTemporal(int loteId, int tipo)	throws Exception;
}

